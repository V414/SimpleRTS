/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects;

import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class NetworkedObject extends GameObject {
    
    protected int mNetworkId;
    
    
    protected NetworkedObject(Scene scene) {
        super(scene);
        mNetworkId = -1;
    }
    
    public NetworkedObject setNetworkId(int id) {
        mNetworkId = id;
        return this;
    }
    
    public int getNetworkId() {
        return mNetworkId;
    }

    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.NetworkedObject.getID();
    }

    @Override
    public String getTypeString() {
        return "NetworkedObject";
    }
    
}
