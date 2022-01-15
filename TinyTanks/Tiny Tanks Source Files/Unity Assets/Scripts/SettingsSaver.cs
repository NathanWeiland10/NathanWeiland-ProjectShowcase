// This code is used to save settings of the game between different scenes

using UnityEngine;

public class SettingsSaver : MonoBehaviour
{
    public int currentVolumeCounter = 8;

    public int getCurrentVolumeCounter()
    {
        return currentVolumeCounter;
    }

    public void setCurrentVolumeCounter(int volCount)
    {
        currentVolumeCounter = volCount;
    }

}
