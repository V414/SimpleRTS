package com.dinasgames.main.objects.entities.units;

import com.dinasgames.main.objects.entities.Entity;
import com.dinasgames.main.objects.GameObjectType;
import com.dinasgames.main.objects.LogicEvents;
import com.dinasgames.main.commands.Command;
import com.dinasgames.main.scenes.Scene;
import com.dinasgames.engine.system.Clock;
import com.dinasgames.engine.system.Time;
import java.util.ArrayList;
import java.util.List;

public class Unit extends Entity implements LogicEvents {
    
    /**
     * Speed in meters per second.
     */
    protected float mSpeed;
    
    /**
     * Acceleration in meters per second.
     */
    protected float mAcceleration;
    
    /**
     * Deceleration in meters per second.
     */
    protected float mDeceleration;
    
    /**
     * Damage that is applied each time this unit attacks another.
     */
    protected float mDamage;
    
    /**
     * Range in pixels that this unit can attack from.
     */
    protected float mRange;
    
    /**
     * The time this unit waits before attacking again.
     */
    protected Time mAttackTime;
    
    /**
     * The time it takes for this unit to reload after an attack.
     */
    protected Time mReloadTime;
    
    /**
     * A clock used to check whether we have reloaded.
     */
    protected Clock mReloadClock;
    
    /**
     * The ammo that this Unit has left.
     */
    protected int mAmmo;
    
    /**
     * The maximum ammo that this unit can store.
     */
    protected int mMaxAmmo;
    
    /**
     * A list of commands for this unit.
     */
    protected List<Command> mCommands;
    
    /**
     * Events that can be listened for.
     */
    public interface Events {
        
        public void onNewCommand(Command command);  // << Called when a new command is issued to a unit. Note: This can be null!
        
    }
    
    protected Unit(Scene scene) {
        
        super(scene);
        
        mDeceleration   = 0.f;
        mAcceleration   = 0.f;
        mSpeed          = 0.f;
        mDamage         = 0.f;
        mRange          = 0.f;
        mAttackTime     = null;
        mReloadTime     = null;
        mReloadClock    = null;
        mAmmo           = 0;
        mMaxAmmo        = 0;
        mCommands       = new ArrayList();
        
    }
    
    /**
     * Issue a new command to a unit. Note: A null command will stop all commands.
     * @param command
     * @param clearCurrent 
     */
    public void issueCommand(Command command, boolean clearCurrent) {
        
        if(command == null) {
            mCommands.clear();
            return;
        }
        
        if(clearCurrent) {
            mCommands.clear();
        }
        
        mCommands.add(command);
        
    }
    
    /**
     * Issue a command clearing the current command list. Note: A null command will stop all commands.
     * @param command 
     */
    public void issueCommand(Command command) {
        issueCommand(command,true);
    }
    
    @Override
    public void onTick(Time timePassed) {
        
        super.onTick(timePassed);
        
        // Update commands
        if(mCommands.size() > 0) {
            Command currentCommand = mCommands.get(0);
            if(currentCommand != null) {
                
                // Check if this command has been issued yet
                if(!currentCommand.hasBeenIssued()) {
                    
                    // Event
                    for(Object listener : mListeners) {
                        if(listener instanceof Events) {
                            ((Events)listener).onNewCommand(currentCommand);
                        }
                    }
                    
                    // Issue the command
                    currentCommand.issue(this);
                    
                }else{
                  
                  currentCommand.update(timePassed);
                  
                  // Check if the command has been completed
                  if(currentCommand.isCompleted()) {
                    Command newCommand = currentCommand.onCompleted();
                    if(newCommand != null) {
                      mCommands.add(newCommand);
                    }
                    mCommands.remove(0);
                  }
                  
                }
                
                // Other command logic
                
                
            }
        }
        
    }
    
    /**
     * How fast this unit moves in meters per second.
     * @return 
     */
    public float getSpeed() {
        return mSpeed;
    }
    
    /**
     * How fast this unit speeds up in meters per second.
     * @return 
     */
    public float getAcceleration() {
        return mAcceleration;
    }
    
    /**
     * How fast this unit slows down in meters per second.
     * @return 
     */
    public float getDeceleration() {
        return mDeceleration;
    }
    
//    protected void moveUnit(){
//
//        if(Point.distance(mPosition, mTargetPosition) > mSpeed) {
////          Vector2f oldPosition = new Vector2f(mPosition);
//          
//          mPosition.add( Point.inDirection(mSpeed, Point.direction(mPosition, mTargetPosition)) );
//          mRotation = -(float)Point.direction(mPosition, mTargetPosition);
//          
////          if(checkColliding() == true){
////            mPosition = oldPosition;
////          }
//        }
//        
////        if(Math.abs(Point.distance(mPosition, mTargetPosition)) > mSpeed) {
////            
////            float angle = (float)Point.direction(mPosition, mTargetPosition);
////            
////            mPosition.x += Point.inDirectionX ( mSpeed, angle );
////            mPosition.y += Point.inDirectionY( mSpeed, angle );
////            
////            System.out.println(angle);
////            
////        }
////        
////      if((mPosition.x < mTargetPosition.x - mSpeed ||
////              mPosition.x > mTargetPosition.x + mSpeed) &&
////              (mPosition.y < mTargetPosition.y - mSpeed ||
////              mPosition.y > mTargetPosition.y + mSpeed)){
////        double b = mTargetPosition.y - mPosition.y;
////        double a = mTargetPosition.x - mPosition.x;
////        mRotation = Math.round(Math.toDegrees(Math.atan2(b, a)));
////
////        //Preventing angles going into minus numbers when travelling backwards
////        if(mRotation < 0){
////            mRotation += 360;
////        }
////        
////        float nX = (float) (mPosition.x + (mSpeed * Math.cos(Math.toRadians(mRotation))));
////        float nY = (float) (mPosition.y + (mSpeed * Math.sin(Math.toRadians(mRotation))));
////
////        mPosition.x = nX;
////        mPosition.y = nY;
////      }
//    }
    
//    protected boolean checkColliding(){
//      for(GameObject gameObject : mScene.getObjectsList()){
//        if(gameObject != null && gameObject.hasType(GameObjectType.Entity)){
//          Entity entity = (Entity) gameObject;
//          if(entity.getID() != mID){
//            
//            Box.Points box2 = Box.calculateMatrix(new Box.MatrixInput(
//                    entity.getPosition().x, entity.getPosition().y, 
//                    entity.getSize().x, entity.getSize().y, entity.getRotation(), 
//                    entity.getSize().x/2, entity.getSize().y/2
//            ));
//
//            if(mBoundingBox.containsAny(box2)){
//               return true;
//            }
//          }
//        }
//      }
//      
//      return false;
//    }
    
//    protected void shootTarget(){
//      
//      if(mTargetDistance < mMaxRange){
//        if(mReloadingTime < 0){
//          target.damage(mDamage);
//          if(target.getHealth() <= 0){
//            target.kill();
//            mScene.remove(target);
//          }
//          mReloadingTime = mMaxReloadingTime;
//        }else{
//          --mReloadingTime;
//        }
//      }
//    }
//    
//    protected void setTarget(GameObject[] list){
//      
//      int distanceToEnemy = 99999;
//      
//      for(GameObject gameObject : list){
//        if(gameObject != null && gameObject.hasType(GameObjectType.Unit)){
//          Entity unit = (Entity) gameObject;
//          
//          if(unit.getOwner() != this.getOwner()){
//            Vector2f thisOrigin = new Vector2f(mPosition.x + mSize.x/2, 
//                    mPosition.y + mSize.y/2);
//            
//            Vector2f unitOrigin = new Vector2f(unit.getPosition().x + unit.getSize().x/2, 
//                    unit.getPosition().y + unit.getSize().y/2);
//            
//            
//            int distanceX;
//            if(unitOrigin.x > thisOrigin.x){
//                distanceX = Math.round(thisOrigin.x - unitOrigin.x);
//            }else{
//                distanceX = Math.round(thisOrigin.x - unitOrigin.x);
//            }
//
//            int distanceY;
//            if(unitOrigin.y > thisOrigin.y){
//                distanceY = Math.round(thisOrigin.y - unitOrigin.y);
//            }else{
//                distanceY = Math.round(thisOrigin.y - unitOrigin.y);
//            }
//
//            int distanceFromUnitSquared = (distanceX*distanceX) + (distanceY*distanceY);
//            int distanceFromUnit = (int) Math.sqrt(distanceFromUnitSquared);
//
//            if(distanceFromUnit < distanceToEnemy){
//                target = unit;
//                mTargetDistance = distanceFromUnit;
//                distanceToEnemy = distanceFromUnit;
//            }
//          }
//        }
//      }
//    }
    
    @Override
    public int getTypeID() {
        return super.getTypeID() | GameObjectType.Unit.getID();
    }
    
    @Override
    public String getTypeString() {
        return "Unit";
    }
    
    
    @Override
    protected void recalculateBoundingBox() {
        super.recalculateBoundingBox();
        mBoundingBox.setSize(mWidth, mHeight);
    }
    
//    public Vector2f getTargetPosition(){
//      return mTargetPosition;
//    }
//    
//    public void setTargetPosition(Vector2f mTargetPosition){
//      this.mTargetPosition = mTargetPosition;
//    }
//    
//    public void setTargetPosition(float x, float y){
//      this.mTargetPosition.x = x;
//      this.mTargetPosition.y = y;
//    }
//    
//    @Override
//    public void setPosition(float x, float y) {
//        super.setPosition(x,y);
//        setTargetPosition(x,y);
//    }
    
}

//package com.dinasgames.objects;
//
//import java.awt.Color;
//import java.awt.geom.Rectangle2D;
//
//public class Unit extends GameObject{
//  
//  protected int healthMax;
//  protected int healthNow;
//  
//  protected int ammoMax;
//  protected int ammoNow;
//  
//  protected boolean isSelected;
//  protected DrawShapes[] unitStatusBars;
//  
//  public Unit(){
//    
//  }
//  
//  protected void drawUnitStatusBars(){
//    unitStatusBars = new DrawShapes[3];
//    
//    Rectangle2D fillHealthBar = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            getObjectWidth(), 4);
//    unitStatusBars[0] = new DrawShapes(fillHealthBar, Color.black, "fill");
//    
//    float percentPlayerHealth = (float) healthNow/healthMax;
//    
//    Rectangle2D fillHealthBarHealth = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            percentPlayerHealth*getObjectWidth(), 4);
//    unitStatusBars[1] = new DrawShapes(fillHealthBarHealth, Color.red, "fill");
//    
//    
//    Rectangle2D drawBody = new Rectangle2D.Float(
//            mX-cameraX, mY-cameraY-8, 
//            getObjectWidth(), 4);
//    unitStatusBars[2] = new DrawShapes(drawBody, Color.black, "draw");
//  }
//
//  //Getters
//  
//  public DrawShapes[] getStatusBars(){
//    return unitStatusBars;
//  }
//  
//  public boolean getIsSelected(){
//    return isSelected;
//  }
//  
//  public int getAmmoNow(){
//    return ammoNow;
//  }
//  
//  public int getAmmoMax(){
//    return ammoMax;
//  }
//  
//  public int getHealthNow(){
//    return healthNow;
//  }
//  
//  public int getHealthMax(){
//    return healthMax;
//  }
//  
//  
//  //Setters
//  
//  public void setIsSelected(boolean isSelected){
//    this.isSelected = isSelected;
//  }
//  
//  public void setAmmoNow(int ammoNow){
//    this.ammoNow = ammoNow;
//  }
//  
//  public void setHealthNow(int healthNow){
//    this.healthNow = healthNow;
//  }
//}
