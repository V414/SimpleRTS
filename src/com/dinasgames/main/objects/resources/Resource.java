package com.dinasgames.main.objects.resources;

import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.scenes.Scene;

public class Resource extends GameObject{
  
  protected String mResourceType;
  protected int mResourceQuantity;
  
  protected Resource(Scene scene){
    mScene = scene;
    mRenderer = mScene.getRenderer();
  }
  
  @Override
  public void onDestroy() {

  }

  @Override
  public void onTick(double time) {

  }

  @Override
  public void onRender() {

  }
  
  @Override
  public int getTypeID() {
    return GameObjectType.Resource.getID();
  }

  @Override
  public String getTypeString() {
      return "Resource";
  }
  
  //Getters
  
  public int getResourceQuantity(){
    return mResourceQuantity;
  }
  
  public String getResourceType(){
    return mResourceType;
  }
  
  //Setters
  
}