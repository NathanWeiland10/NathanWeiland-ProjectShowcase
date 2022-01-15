using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CreativeButtons : MonoBehaviour
{
    // Begin Citation:
    // From: https://www.youtube.com/watch?v=m1lBHP5lxeY
    // This script is used to make the hit area of the button match the sprite used on the button:
    void Start()
    {
        this.GetComponent<Image>().alphaHitTestMinimumThreshold = 0.1f;
    }
    // End Citation
}
