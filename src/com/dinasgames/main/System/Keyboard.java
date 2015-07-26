///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dinasgames.main.System;
//
//import java.awt.event.KeyEvent;
//import static java.awt.event.KeyEvent.*;
//
///**
// *
// * @author Jack
// */
//public class Keyboard {
//    
//    public static enum Key
//    {
//        Unknown(-1),
//        A(0), // 65
//        B(1),
//        C(2),
//        D(3),
//        E(4),
//        F(5),
//        G(6),
//        H(7),
//        I(8),
//        J(9),
//        K(10),
//        L(11),
//        M(12),
//        N(13),
//        O(14),
//        P(15),
//        Q(16),
//        R(17),
//        S(18),
//        T(19),
//        U(20),
//        V(21),
//        W(22),
//        X(23),
//        Y(24),
//        Z(25),
//        Num0(26),
//        Num1(27),
//        Num2(28),
//        Num3(29),
//        Num4(30),
//        Num5(31),
//        Num6(32),
//        Num7(33),
//        Num8(34),
//        Num9(35),
//        Escape(36),
//        Control(37),
//        Shift(38),
//        Alt(39),
//        //LSystem(40),
//        //RControl(41),
//        //RShift(42),
//        //RAlt(43),
//        //RSystem(44),
//        Menu(45),
//        LBracket(46),
//        RBracket(47),
//        SemiColon(48),
//        Comma(49),
//        Period(50),
//        Quote(51),
//        Slash(52),
//        BackSlash(53),
//        Tilde(54),
//        Equal(55),
//        Dash(56),
//        Space(57),
//        Return(58),
//        BackSpace(59),
//        Tab(60),
//        PageUp(61),
//        PageDown(62),
//        End(63),
//        Home(64),
//        Insert(65),
//        Delete(66),
//        Add(67),
//        Subtract(68),
//        Multiply(69),
//        Divide(70),
//        Left(71),
//        Right(72),
//        Up(73),
//        Down(74),
//        Numpad0(75),
//        Numpad1(76),
//        Numpad2(77),
//        Numpad3(78),
//        Numpad4(79),
//        Numpad5(80),
//        Numpad6(81),
//        Numpad7(82),
//        Numpad8(83),
//        Numpad9(84),
//        F1(85),
//        F2(86),
//        F3(87),
//        F4(88),
//        F5(89),
//        F6(90),
//        F7(91),
//        F8(92),
//        F9(93),
//        F10(94),
//        F11(95), // 122
//        F12(96),
//        F13(97),
//        F14(98),
//        F15(99),
//        Pause(100),
//        KeyCount(101);
//        
//        private int id;
//        
//        Key(int id) {
//            this.id = id;
//        }
//        
//        public int getID() {
//            return id;
//        }
//        
//    };
//    
//    private static final int MAX_KEYS = Key.KeyCount.getID();
//    
//    protected static boolean[] mKeyState = null;
//    
//    public static void initKeys() {
//        if(mKeyState == null) {
//            mKeyState = new boolean[MAX_KEYS];
//            for(int i = 0; i < MAX_KEYS; i ++) {
//                mKeyState[i] = false;
//            }
//        }
//    }
//    
//    public static Key translate(int code) {
//        switch(code) {
//           case VK_A: return Key.A;
//           case VK_B: return Key.B;
//           case VK_C: return Key.C;
//           case VK_D: return Key.D;
//           case VK_E: return Key.E;
//           case VK_F: return Key.F;
//           case VK_G: return Key.G;
//           case VK_H: return Key.H;
//           case VK_I: return Key.I;
//           case VK_J: return Key.J;
//           case VK_K: return Key.K;
//           case VK_L: return Key.L;
//           case VK_M: return Key.M;
//           case VK_N: return Key.N;
//           case VK_O: return Key.O;
//           case VK_P: return Key.P;
//           case VK_Q: return Key.Q;
//           case VK_R: return Key.R;
//           case VK_S: return Key.S;
//           case VK_T: return Key.T;
//           case VK_U: return Key.U;
//           case VK_V: return Key.V;
//           case VK_W: return Key.W;
//           case VK_X: return Key.X;
//           case VK_Y: return Key.Y;
//           case VK_Z: return Key.Z;
//           case VK_0: return Key.Num0;
//           case VK_1: return Key.Num1;
//           case VK_2: return Key.Num2;
//           case VK_3: return Key.Num3;
//           case VK_4: return Key.Num4;
//           case VK_5: return Key.Num5;
//           case VK_6: return Key.Num6;
//           case VK_7: return Key.Num7;
//           case VK_8: return Key.Num8;
//           case VK_9: return Key.Num9;
//           case VK_ESCAPE: return Key.Escape;
//           case VK_CONTROL: return Key.Control;
//           case VK_SHIFT: return Key.Shift;
//           case VK_ALT: return Key.Alt;
//           case VK_BRACELEFT: return Key.LBracket;
//           case VK_BRACERIGHT: return Key.RBracket;
//           case VK_SEMICOLON: return Key.SemiColon;
//           case VK_COMMA: return Key.Comma;
//           case VK_PERIOD: return Key.Period;
//           case VK_QUOTE: return Key.Quote;
//           case VK_SLASH: return Key.Slash;
//           case VK_BACK_SLASH: return Key.BackSlash;
//           case VK_DEAD_TILDE: return Key.Tilde;
//           case VK_EQUALS: return Key.Equal;
//           case VK_SPACE: return Key.Space;
//           case VK_ENTER: return Key.Return;
//           case VK_BACK_SPACE: return Key.BackSpace;
//           case VK_TAB: return Key.Tab;
//           case VK_PAGE_UP: return Key.PageUp;
//           case VK_PAGE_DOWN: return Key.PageDown;
//           case VK_END: return Key.End;
//           case VK_HOME: return Key.Home;
//           case VK_INSERT: return Key.Insert;
//           case VK_DELETE: return Key.Delete;
//           case VK_ADD: return Key.Add;
//           case VK_SUBTRACT: return Key.Subtract;
//           case VK_MULTIPLY: return Key.Multiply;
//           case VK_DIVIDE: return Key.Divide;
//           case VK_LEFT: return Key.Left;
//           case VK_RIGHT: return Key.Right;
//           case VK_UP: return Key.Up;
//           case VK_DOWN: return Key.Down;
//           case VK_NUMPAD0: return Key.Numpad0;
//           case VK_NUMPAD1: return Key.Numpad1;
//           case VK_NUMPAD2: return Key.Numpad2;
//           case VK_NUMPAD3: return Key.Numpad3;
//           case VK_NUMPAD4: return Key.Numpad4;
//           case VK_NUMPAD5: return Key.Numpad5;
//           case VK_NUMPAD6: return Key.Numpad6;
//           case VK_NUMPAD7: return Key.Numpad7;
//           case VK_NUMPAD8: return Key.Numpad8;
//           case VK_NUMPAD9: return Key.Numpad9;
//           case VK_F1: return Key.F1;
//           case VK_F2: return Key.F2;
//           case VK_F3: return Key.F3;
//           case VK_F4: return Key.F4;
//           case VK_F5: return Key.F5;
//           case VK_F6: return Key.F6;
//           case VK_F7: return Key.F7;
//           case VK_F8: return Key.F8;
//           case VK_F9: return Key.F9;
//           case VK_F10: return Key.F10;
//           case VK_F11: return Key.F11;
//           case VK_F12: return Key.F12;
//           case VK_F13: return Key.F13;
//           case VK_F14: return Key.F14;
//           case VK_F15: return Key.F15;
//           case VK_PAUSE: return Key.Pause;
//        }
//        return Key.Unknown;
//    }
//    
//    public static int translate(Key key) {
//        switch(key) {
//            case Unknown: return -1;
//            case A: return VK_A;
//            case B: return VK_B;
//            case C: return VK_C;
//            case D: return VK_D;
//            case E: return VK_E;
//            case F: return VK_F;
//            case G: return VK_G;
//            case H: return VK_H;
//            case I: return VK_I;
//            case J: return VK_J;
//            case K: return VK_K;
//            case L: return VK_L;
//            case M: return VK_M;
//            case N: return VK_N;
//            case O: return VK_O;
//            case P: return VK_P;
//            case Q: return VK_Q;
//            case R: return VK_R;
//            case S: return VK_S;
//            case T: return VK_T;
//            case U: return VK_U;
//            case V: return VK_V;
//            case W: return VK_W;
//            case X: return VK_X;
//            case Y: return VK_Y;
//            case Z: return VK_Z;
//            case Num0: return VK_0;
//            case Num1: return VK_1;
//            case Num2: return VK_2;
//            case Num3: return VK_3;
//            case Num4: return VK_4;
//            case Num5: return VK_5;
//            case Num6: return VK_6;
//            case Num7: return VK_7;
//            case Num8: return VK_8;
//            case Num9: return VK_9;
//            case Escape: return VK_ESCAPE;
//            case Control: return VK_CONTROL;
//            case Shift: return VK_SHIFT;
//            case Alt: return VK_ALT;
//            //case LSystem: return VK_LSYSTEM;
//            //case RControl: return VK_CONTROL;
//            //case RShift: return VK_RSHIFT;
//            //case RAlt: return VK_RALT;
//            //case RSystem: return VK_RSYSTEM;
//            //case Menu: return VK_MENU;
//            case LBracket: return VK_BRACELEFT;
//            case RBracket: return VK_BRACERIGHT;
//            case SemiColon: return VK_SEMICOLON;
//            case Comma: return VK_COMMA;
//            case Period: return VK_PERIOD;
//            case Quote: return VK_QUOTE;
//            case Slash: return VK_SLASH;
//            case BackSlash: return VK_BACK_SLASH;
//            case Tilde: return VK_DEAD_TILDE;
//            case Equal: return VK_EQUALS;
//            case Dash: return VK_SUBTRACT;
//            case Space: return VK_SPACE;
//            case Return: return VK_ENTER;
//            case BackSpace: return VK_BACK_SPACE;
//            case Tab: return VK_TAB;
//            case PageUp: return VK_PAGE_UP;
//            case PageDown: return VK_PAGE_DOWN;
//            case End: return VK_END;
//            case Home: return VK_HOME;
//            case Insert: return VK_INSERT;
//            case Delete: return VK_DELETE;
//            case Add: return VK_ADD;
//            case Subtract: return VK_SUBTRACT;
//            case Multiply: return VK_MULTIPLY;
//            case Divide: return VK_DIVIDE;
//            case Left: return VK_LEFT;
//            case Right: return VK_RIGHT;
//            case Up: return VK_UP;
//            case Down: return VK_DOWN;
//            case Numpad0: return VK_NUMPAD0;
//            case Numpad1: return VK_NUMPAD1;
//            case Numpad2: return VK_NUMPAD2;
//            case Numpad3: return VK_NUMPAD3;
//            case Numpad4: return VK_NUMPAD4;
//            case Numpad5: return VK_NUMPAD5;
//            case Numpad6: return VK_NUMPAD6;
//            case Numpad7: return VK_NUMPAD7;
//            case Numpad8: return VK_NUMPAD8;
//            case Numpad9: return VK_NUMPAD9;
//            case F1: return VK_F1;
//            case F2: return VK_F2;
//            case F3: return VK_F3;
//            case F4: return VK_F4;
//            case F5: return VK_F5;
//            case F6: return VK_F6;
//            case F7: return VK_F7;
//            case F8: return VK_F8;
//            case F9: return VK_F9;
//            case F10: return VK_F10;
//            case F11: return VK_F11;
//            case F12: return VK_F12;
//            case F13: return VK_F13;
//            case F14: return VK_F14;
//            case F15: return VK_F15;
//            case Pause: return VK_PAUSE;
//            case KeyCount: return -1;
//        }
//        return -1;
//    }
//    
//    public static boolean isKeyPressed(Key key) {
//        initKeys();
//        return mKeyState[key.getID()];
//    }
//    
//    public static void onKeyPressed(KeyEvent e) {
//        initKeys();
//        int code = e.getKeyCode();
//        Key key = translate(code);
//        if(key.getID() >= 0 && key.getID() <= MAX_KEYS) {
//            mKeyState[key.getID()] = true;
//        }
//    }
//    
//    public static void onKeyReleased(KeyEvent e) {
//        initKeys();
//        int code = e.getKeyCode();
//        Key key = translate(code);
//        if(key.getID() >= 0 && key.getID() <= MAX_KEYS) {
//            mKeyState[key.getID()] = false;
//        }
//    }
//    
//}
