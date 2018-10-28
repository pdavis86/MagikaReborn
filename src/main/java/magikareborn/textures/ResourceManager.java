package magikareborn.textures;

public class ResourceManager {

    public static String getBlockTexturePathStem(){
        return "textures/blocks";
    }

    public static String getBlockTexturePath(String textureFileName){
        return String.format("%s/%s", getBlockTexturePathStem(), textureFileName);
    }
}
