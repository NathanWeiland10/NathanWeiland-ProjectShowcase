// This code is used to allow the player to change the volume of the game using buttons

using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Audio;

public class VolumeSetter : MonoBehaviour
{
    SettingsSaver settingsSaver;
    public AudioMixer audioMixer;
    public GameObject[] volumeCircles;
    public int volumeCounter = 8;
    public float currentVolume = 0.0f;
    public Sprite filledCircle;
    public Sprite grayCircle;
    public string changeSound;

    void Awake()
    {
        settingsSaver = FindObjectOfType<SettingsSaver>();
        volumeCounter = settingsSaver.getCurrentVolumeCounter();

        for (int i = 0; i < 10; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = grayCircle;
        }
        for (int i = 0; i < volumeCounter; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = filledCircle;
        }
        SetVolume(volumeCounter);
    }

    public void addVolume()
    {
        if (volumeCounter != 10)
        {
            volumeCounter ++;
        }
        for (int i = 0; i < 10; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = grayCircle;
        }
        for (int i = 0; i < volumeCounter; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = filledCircle;
        }
        SetVolume(volumeCounter);
        settingsSaver.setCurrentVolumeCounter(volumeCounter);
        FindObjectOfType<AudioManager>().Play(changeSound);
    }

    public void subtractVolume()
    {
        if (volumeCounter != 0)
        {
            volumeCounter--;
        }
        for (int i = 0; i < 10; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = grayCircle;
        }
        for (int i = 0; i < volumeCounter; i++)
        {
            volumeCircles[i].gameObject.GetComponent<Image>().sprite = filledCircle;
        }
        SetVolume(volumeCounter);
        settingsSaver.setCurrentVolumeCounter(volumeCounter);
        FindObjectOfType<AudioManager>().Play(changeSound);
    }
    public void SetVolume(int volume)
    {
        switch(volume)
        {
            case 0:
                currentVolume = -80f;
                break;
            case 1:
                currentVolume = -70f;
                break;
            case 2:
                currentVolume = -60f;
                break;
            case 3:
                currentVolume = -50f;
                break;
            case 4:
                currentVolume = -40f;
                break;
            case 5:
                currentVolume = -30f;
                break;
            case 6:
                currentVolume = -20f;
                break;
            case 7:
                currentVolume = -10f;
                break;
            case 8:
                currentVolume = 0f;
                break;
            case 9:
                currentVolume = 10f;
                break;
            case 10:
                currentVolume = 20f;
                break;
        }
        audioMixer.SetFloat("Volume", currentVolume);
    }

}
