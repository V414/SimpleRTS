/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.engine.graphics;

import com.dinasgames.engine.graphics.shapes.RectangleShape;
import com.dinasgames.main.maps.Map;
import com.dinasgames.main.maps.Map.MapChunk;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jack
 */
public class MapDrawer extends Renderable {
    
    protected List<Texture> mTextures;
    protected List<RectangleShape> mShapes;
    protected Map mMap;
    
    public MapDrawer(Map map) {
        mMap = map;
        mTextures = new ArrayList();
        mShapes = new ArrayList();
    }
    
    @Override
    public void onAdd() {
        
        // Generate Texture(s) & RectangleShape(s)
        int chunkSize = mMap.getChunkSize();
        for(int x = 0; x < mMap.getWidth(); x++) {
            for(int y = 0; y < mMap.getHeight(); y++) {
                
                MapChunk chunk = mMap.getChunkAt(x,y);
                
                if(chunk == null) {
                    continue;
                }

                // Create a texture from the chunk image
                Texture tex = new Texture();
                tex.loadFromImage(chunk.mImage);
                
                mTextures.add(tex);
                
                // Create a rectangleshape with this texture on it
                RectangleShape r = new RectangleShape(chunkSize, chunkSize);
                
                r.setTexture(tex);
                r.setDepth(1000);
                r.setPosition(chunk.mChunkX * chunkSize, chunk.mChunkY * chunkSize);
                r.render(mRenderer);
                                    
                mShapes.add(r);

            }
        }
        
        // Clear the map to reduce memory usage
        mMap.cleanup();
        
    }
    
    @Override
    public void onRemove() {
        
        // Remove textures
        for(Texture t : mTextures) {
            t.destruct();
        }
        
        // Remove shapes
        for(RectangleShape r : mShapes) {
            r.remove();
        }
        
    }
    
}
