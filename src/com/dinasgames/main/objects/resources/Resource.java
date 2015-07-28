package com.dinasgames.main.objects.resources;

import com.dinasgames.lwjgl.util.Renderer;
import com.dinasgames.main.objects.GameObject;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.LogicEvents;
import com.dinasgames.main.objects.RenderEvents;
import com.dinasgames.main.objects.SceneEvents;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.main.system.Time;

public class Resource extends GameObject implements SceneEvents, RenderEvents, LogicEvents{
  
  protected String mResourceType;
  protected int mResourceQuantity;
  
  protected Resource(Scene scene){
    super(scene);
    
    
  }

  @Override
  public void onTick(Time timePassed) {

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

  @Override
  public void onSceneAdd(Scene scene) {}

  @Override
  public void onSceneRemove(Scene scene) {}

  @Override
  public void onRenderAdd(Renderer renderer) {}

  @Override
  public void onRenderRemove(Renderer renderer) {}

  @Override
  public void onRenderUpdate(Renderer renderer) {}
  
}