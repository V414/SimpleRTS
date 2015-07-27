/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.entities.units.infantry;

import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.entities.units.Unit;
import com.dinasgames.main.scenes.Scene;

/**
 *
 * @author Jack
 */
public class Infantry extends Unit {
  
    public Infantry(Scene scene) {
        super(scene);
    }
  
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Infantry.getID();
    }

    @Override
    public String getTypeString() {
        return "Infantry";
    }


}