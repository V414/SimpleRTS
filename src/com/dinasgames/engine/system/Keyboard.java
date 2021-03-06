package com.dinasgames.engine.system;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.util.HashMap;
import java.util.Map;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Jack
 */
public class Keyboard {
    
    public static enum Key
    {
        
        Escape,
        Enter,
        Tab,
        Backspace,
        Insert,
        Delete,
        Right,
        Left,
        Down,
        Up,
        PageUp,
        PageDown,
        Home,
        End,
        CapsLock,
        ScrollLock,
        NumLock,
        PrintScreen,
        Pause,
        F1,
        F2,
        F3,
        F4,
        F5,
        F6,
        F7,
        F8,
        F9,
        F10,
        F11,
        F12,
        F13,
        F14,
        F15,
        F16,
        F17,
        F18,
        F19,
        F20,
        F21,
        F22,
        F23,
        F24,
        F25,
        Kp0,
        Kp1,
        Kp2,
        Kp3,
        Kp4,
        Kp5,
        Kp6,
        Kp7,
        Kp8,
        Kp9,
        KpDecimal,
        KpDivide,
        KpMultiply,
        KpSubtract,
        KpAdd,
        KpEnter,
        KpEqual,
        LeftShift,
        LeftControl,
        LeftAlt,
        LeftSuper,
        RightShift,
        RightControl,
        RightAlt,
        RightSuper,
        Menu,
        Space,
        Apostrophe,
        Comma,
        Minus,
        Period,
        Slash,
        Zero,
        One,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Semicolon,
        Equal,
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L,
        M,
        N,
        O,
        P,
        Q,
        R,
        S,
        T,
        U,
        V,
        W,
        X,
        Y,
        Z,
        LeftBracket,
        Backslash,
        RightBracket,
        GraveAccent,
        World1,
        World2,
        
        Unknown
        
    };
    
    
    public static final int[] nativeList = new int[]{ 
        
        GLFW_KEY_ESCAPE,
        GLFW_KEY_ENTER,
        GLFW_KEY_TAB,
        GLFW_KEY_BACKSPACE,
        GLFW_KEY_INSERT,
        GLFW_KEY_DELETE,
        GLFW_KEY_RIGHT,
        GLFW_KEY_LEFT,
        GLFW_KEY_DOWN,
        GLFW_KEY_UP,
        GLFW_KEY_PAGE_UP,
        GLFW_KEY_PAGE_DOWN,
        GLFW_KEY_HOME,
        GLFW_KEY_END,
        GLFW_KEY_CAPS_LOCK,
        GLFW_KEY_SCROLL_LOCK,
        GLFW_KEY_NUM_LOCK,
        GLFW_KEY_PRINT_SCREEN,
        GLFW_KEY_PAUSE,
        GLFW_KEY_F1,
        GLFW_KEY_F2,
        GLFW_KEY_F3,
        GLFW_KEY_F4,
        GLFW_KEY_F5,
        GLFW_KEY_F6,
        GLFW_KEY_F7,
        GLFW_KEY_F8,
        GLFW_KEY_F9,
        GLFW_KEY_F10,
        GLFW_KEY_F11,
        GLFW_KEY_F12,
        GLFW_KEY_F13,
        GLFW_KEY_F14,
        GLFW_KEY_F15,
        GLFW_KEY_F16,
        GLFW_KEY_F17,
        GLFW_KEY_F18,
        GLFW_KEY_F19,
        GLFW_KEY_F20,
        GLFW_KEY_F21,
        GLFW_KEY_F22,
        GLFW_KEY_F23,
        GLFW_KEY_F24,
        GLFW_KEY_F25,
        GLFW_KEY_KP_0,
        GLFW_KEY_KP_1,
        GLFW_KEY_KP_2,
        GLFW_KEY_KP_3,
        GLFW_KEY_KP_4,
        GLFW_KEY_KP_5,
        GLFW_KEY_KP_6,
        GLFW_KEY_KP_7,
        GLFW_KEY_KP_8,
        GLFW_KEY_KP_9,
        GLFW_KEY_KP_DECIMAL,
        GLFW_KEY_KP_DIVIDE,
        GLFW_KEY_KP_MULTIPLY,
        GLFW_KEY_KP_SUBTRACT,
        GLFW_KEY_KP_ADD,
        GLFW_KEY_KP_ENTER,
        GLFW_KEY_KP_EQUAL,
        GLFW_KEY_LEFT_SHIFT,
        GLFW_KEY_LEFT_CONTROL,
        GLFW_KEY_LEFT_ALT,
        GLFW_KEY_LEFT_SUPER,
        GLFW_KEY_RIGHT_SHIFT,
        GLFW_KEY_RIGHT_CONTROL,
        GLFW_KEY_RIGHT_ALT,
        GLFW_KEY_RIGHT_SUPER,
        GLFW_KEY_MENU,
        GLFW_KEY_SPACE,
        GLFW_KEY_APOSTROPHE,
        GLFW_KEY_COMMA,
        GLFW_KEY_MINUS,
        GLFW_KEY_PERIOD,
        GLFW_KEY_SLASH,
        GLFW_KEY_0,
        GLFW_KEY_1,
        GLFW_KEY_2,
        GLFW_KEY_3,
        GLFW_KEY_4,
        GLFW_KEY_5,
        GLFW_KEY_6,
        GLFW_KEY_7,
        GLFW_KEY_8,
        GLFW_KEY_9,
        GLFW_KEY_SEMICOLON,
        GLFW_KEY_EQUAL,
        GLFW_KEY_A,
        GLFW_KEY_B,
        GLFW_KEY_C,
        GLFW_KEY_D,
        GLFW_KEY_E,
        GLFW_KEY_F,
        GLFW_KEY_G,
        GLFW_KEY_H,
        GLFW_KEY_I,
        GLFW_KEY_J,
        GLFW_KEY_K,
        GLFW_KEY_L,
        GLFW_KEY_M,
        GLFW_KEY_N,
        GLFW_KEY_O,
        GLFW_KEY_P,
        GLFW_KEY_Q,
        GLFW_KEY_R,
        GLFW_KEY_S,
        GLFW_KEY_T,
        GLFW_KEY_U,
        GLFW_KEY_V,
        GLFW_KEY_W,
        GLFW_KEY_X,
        GLFW_KEY_Y,
        GLFW_KEY_Z,
        GLFW_KEY_LEFT_BRACKET,
        GLFW_KEY_BACKSLASH,
        GLFW_KEY_RIGHT_BRACKET,
        GLFW_KEY_GRAVE_ACCENT,
        GLFW_KEY_WORLD_1,
        GLFW_KEY_WORLD_2
        
    };
    
    public static final Key[] tranlateList = new Key[]{ 
        
        Key.Escape,
        Key.Enter,
        Key.Tab,
        Key.Backspace,
        Key.Insert,
        Key.Delete,
        Key.Right,
        Key.Left,
        Key.Down,
        Key.Up,
        Key.PageUp,
        Key.PageDown,
        Key.Home,
        Key.End,
        Key.CapsLock,
        Key.ScrollLock,
        Key.NumLock,
        Key.PrintScreen,
        Key.Pause,
        Key.F1,
        Key.F2,
        Key.F3,
        Key.F4,
        Key.F5,
        Key.F6,
        Key.F7,
        Key.F8,
        Key.F9,
        Key.F10,
        Key.F11,
        Key.F12,
        Key.F13,
        Key.F14,
        Key.F15,
        Key.F16,
        Key.F17,
        Key.F18,
        Key.F19,
        Key.F20,
        Key.F21,
        Key.F22,
        Key.F23,
        Key.F24,
        Key.F25,
        Key.Kp0,
        Key.Kp1,
        Key.Kp2,
        Key.Kp3,
        Key.Kp4,
        Key.Kp5,
        Key.Kp6,
        Key.Kp7,
        Key.Kp8,
        Key.Kp9,
        Key.KpDecimal,
        Key.KpDivide,
        Key.KpMultiply,
        Key.KpSubtract,
        Key.KpAdd,
        Key.KpEnter,
        Key.KpEqual,
        Key.LeftShift,
        Key.LeftControl,
        Key.LeftAlt,
        Key.LeftSuper,
        Key.RightShift,
        Key.RightControl,
        Key.RightAlt,
        Key.RightSuper,
        Key.Menu,
        Key.Space,
        Key.Apostrophe,
        Key.Comma,
        Key.Minus,
        Key.Period,
        Key.Slash,
        Key.Zero,
        Key.One,
        Key.Two,
        Key.Three,
        Key.Four,
        Key.Five,
        Key.Six,
        Key.Seven,
        Key.Eight,
        Key.Nine,
        Key.Semicolon,
        Key.Equal,
        Key.A,
        Key.B,
        Key.C,
        Key.D,
        Key.E,
        Key.F,
        Key.G,
        Key.H,
        Key.I,
        Key.J,
        Key.K,
        Key.L,
        Key.M,
        Key.N,
        Key.O,
        Key.P,
        Key.Q,
        Key.R,
        Key.S,
        Key.T,
        Key.U,
        Key.V,
        Key.W,
        Key.X,
        Key.Y,
        Key.Z,
        Key.LeftBracket,
        Key.Backslash,
        Key.RightBracket,
        Key.GraveAccent,
        Key.World1,
        Key.World2
        
    };
    
    public static final int KEY_COUNT = nativeList.length;
    
    public static boolean[] stateList;
    
    public static void initKeys() {
        if(stateList == null) {
            stateList = new boolean[tranlateList.length];
            for(int i = 0; i < tranlateList.length; i ++) {
                stateList[i] = false;
            }
        }
    }
    
    public static int getNativeKeycode(Key key) {
        for(int i = 0; i < tranlateList.length; i ++) {
            if(tranlateList[i] == key) {
                return nativeList[i];
            }
        }
        return -1;
    }
    
    public static Key getTranslatedKey(int code) {
        for(int i = 0; i < nativeList.length; i ++) {
            if(nativeList[i] == code) {
                return tranlateList[i];
            }
        }
        return Key.Unknown;
    }
    
    public static int getIndex(Key key) {
        for(int i = 0; i < tranlateList.length; i ++) {
            if(tranlateList[i] == key) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getIndex(int code) {
        for(int i = 0; i < nativeList.length; i ++) {
            if(nativeList[i] == code) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isKeyPressed(Key key) {
        initKeys();
        return stateList[getIndex(key)];
    }
    
}
