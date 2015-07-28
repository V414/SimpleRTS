/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dinasgames.main.objects.commands;

/**
 *
 * @author Jack
 */
public class Command {
    
    protected boolean mIssued;
    
    public Command() {
        mIssued = false;
    }
    
    public boolean hasBeenIssued() {
        return mIssued;
    }
    
    public void issue(Object self) {
        mIssued = true;
    }
    
}
