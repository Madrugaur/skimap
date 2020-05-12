package com.example.skimap;

import android.content.Context;
import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Scanner;

public class SkiMap {
    private static String[] TRAIL_CLASSIFICATION_STRING_VALUES = {"GREEN","BLUE","BLACK","DOUBLE","PARK","GLADES","MOGULS","LIFT"};
    private Hashtable<String, Integer[]> trail_table;
    private String vector_data, edge_data;
    private Context context_instance;
    private String file_option;
    public SkiMap(Context context, String file_option){
        this.context_instance = context;
        this.file_option = file_option;
        this.trail_table = new Hashtable<String, Integer[]>();
        init();
    }
    private void init(){
        AssetManager asset_manager = this.context_instance.getAssets();
        try{
            vector_data = readStream(asset_manager.open(file_option + ".vdata"));
            edge_data   = readStream(asset_manager.open(file_option + ".edata"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildTrailTable();
    }
    private String readStream(InputStream stream){
        Scanner stream_reader = new Scanner(stream);
        String stream_content = "";
        while(stream_reader.hasNextLine()){
            stream_content += stream_reader.nextLine() + "\n";
        }
        return stream_content;
    }
    private void buildTrailTable(){
        int trail_name_index = 2;
        String[] trails = edge_data.split("\n");
        for (String trail : trails){
            String[] parts = trail.split(" ");
            String name = parts[trail_name_index];
            int tag_count = parts.length - (trail_name_index + 1);
            Integer[] trail_tags = new Integer[tag_count];
            for (int i = trail_name_index + 1; i < tag_count && i < parts.length; i++){
                trail_tags[i] = Integer.parseInt(parts[i]);
            }
            trail_table.put(name, trail_tags);
        }
    }
    private native String getPath(byte user_preferences, String vector_data, String edge_data);
    static {
        System.loadLibrary("SkiMap");
    }
    public String[] requestPath(byte user_preferences){
        String raw_path = getPath(user_preferences, vector_data, edge_data);
        System.out.println(raw_path);
        return new String[0];
    }
    public String[] getTrailClassifications(String key){
        if (!trail_table.contains(key)) return null;

        Integer[] trail_tags = trail_table.get(key);
        int tag_count = trail_tags.length;
        String[] classifications = new String[tag_count];
        for (int i = 0; i < tag_count; i++){
            classifications[i] = TRAIL_CLASSIFICATION_STRING_VALUES[trail_tags[i]];
        }
        return classifications;
    }
}
