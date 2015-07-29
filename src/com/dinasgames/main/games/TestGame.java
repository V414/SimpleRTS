package com.dinasgames.main.games;

import com.dinasgames.main.DebugScreen;
import com.dinasgames.main.Version;
import com.dinasgames.main.players.LocalPlayer;
import com.dinasgames.main.scenes.TestScene;
import com.dinasgames.main.system.Time;
import com.dinasgames.main.system.Timer;

/**
 * An pretty empty test game
 * @author Jack
 */
public class TestGame extends ClientServerGame {
    
    DebugScreen debug;
    DebugScreen.DebugInfo fps, ping, drawcount;
  
    public TestGame() {
      
      
      
    }
    
    @Override
    public void load() {
        
        super.load();
        
        fps = new DebugScreen.DebugInfo("FPS", "Unknown");
        ping = new DebugScreen.DebugInfo("Ping", "Unknown");
        drawcount = new DebugScreen.DebugInfo("Draw Count: ", "Unknown");

        debug = new DebugScreen(getRenderer());
        debug.add(new DebugScreen.DebugInfo( "Version", Version.getString() ));
        debug.add(fps);
        debug.add(ping);
        debug.add(drawcount);

        Timer.every(Time.seconds(.5f), () -> {

          // Update debug info
          fps.value = Integer.toString(mFps);
          ping.value = Integer.toString(mPing) + "ms";
          drawcount.value = Long.toString(getRenderer().getDrawCount());
          
          debug.update();

        });
        
        mPlayer = new LocalPlayer();
        mPlayerList.add(mPlayer);
        
        // Goto the test scene
        setScene(new TestScene(this));
        
    }
    
}
