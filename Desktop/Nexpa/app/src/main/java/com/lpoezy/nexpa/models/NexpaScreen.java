package com.lpoezy.nexpa.models;

/**
 * Created by pksimpson on 17/05/2016.
 */
public class NexpaScreen {

   public enum Screens{
       SPLASH(1000), SIGNIN(1001);

        public int index;

       Screens(int i) {
           index = i;
       }
   }

    public interface OnSwitchScreenListener {
        public void onSwitchScreen(Screens screen);
    }
}
