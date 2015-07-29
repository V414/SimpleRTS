/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.commands;

import com.dinasgames.main.system.Time;

/**
 *
 * @author Jack
 */
public class Command {
    
    protected Object mSelf;
    protected boolean mIssued, mCompleted;
    
    public Command() {
        mIssued = false;
    }
    
    public boolean hasBeenIssued() {
        return mIssued;
    }
    
    public void issue(Object self) {
        mIssued = true;
        mSelf = self;
    }
    
    public boolean isCompleted() {
      return mCompleted;
    }
    
    public void update(Time timePassed) {
      
    }
    
    /**
     * Optional event
     * @return 
     */
    public Command onCompleted() {
      return null;
    }
    
    public void complete() {
      mCompleted = true;
    }
    
}
