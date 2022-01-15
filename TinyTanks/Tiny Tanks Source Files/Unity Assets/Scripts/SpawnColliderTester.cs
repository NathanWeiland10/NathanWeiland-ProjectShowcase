using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnColliderTester : MonoBehaviour
{

    bool canSpawn = true;

    void OnTriggerEnter(Collider c)
    {
        if (c.gameObject.tag == "Enemy")
        {
            canSpawn = false;
        }
    }

    public bool checkCanSpawn()
    {
        return canSpawn;
    }

    private void Update()
    {
        
    }

}
