package boss.zubworkersinc.controls

import android.view.KeyEvent


class ControlKeySettings() {
    enum class ControlEvent() {
        None(),Up(),Down(),Right(),Left(),PutHoney(),PutOil()

    }
    var  eventKeyMap:MutableMap<ControlEvent, Int> = mutableMapOf<ControlEvent, Int>()
    init
    {
        eventKeyMap[ControlEvent.Up]= KeyEvent.KEYCODE_W
        eventKeyMap[ControlEvent.Down]= KeyEvent.KEYCODE_S
        eventKeyMap[ControlEvent.Right]= KeyEvent.KEYCODE_D
        eventKeyMap[ControlEvent.Left]= KeyEvent.KEYCODE_A
        eventKeyMap[ControlEvent.PutHoney]= KeyEvent.KEYCODE_H
        eventKeyMap[ControlEvent.PutOil]= KeyEvent.KEYCODE_O
    }
    constructor(_keyEventMap:MutableMap<ControlEvent, Int> ):this()
    {
        eventKeyMap[ControlEvent.Up]= KeyEvent.KEYCODE_W
        eventKeyMap[ControlEvent.Down]= KeyEvent.KEYCODE_S
        eventKeyMap[ControlEvent.Right]= KeyEvent.KEYCODE_D
        eventKeyMap[ControlEvent.Left]= KeyEvent.KEYCODE_A
        eventKeyMap[ControlEvent.PutHoney]= KeyEvent.KEYCODE_H
        eventKeyMap[ControlEvent.PutOil]= KeyEvent.KEYCODE_O

        for (ce in _keyEventMap.keys)
            if (_keyEventMap.containsKey(ce))
                eventKeyMap[ce]=_keyEventMap[ce] as Int
    }
}

/*
    public class ControlKeySetting
    {
        public enum ControlEvent : byte
        {
        }


    }
}

*/