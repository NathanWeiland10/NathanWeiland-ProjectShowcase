using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CheatMenu : MonoBehaviour
{

    PlayerTank player;

    void Start()
    {
        player = FindObjectOfType<PlayerTank>();
    }

    public void givePlayerPoints()
    {
        player.addPoints(1000);
    }

}
