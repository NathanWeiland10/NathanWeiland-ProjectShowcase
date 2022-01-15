using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bomb : MonoBehaviour
{

    public float destroyAfterSeconds;
    public GameObject explosionEffect;
    public int explosionDamage;

    void Update()
    {
        destroyAfterSeconds -= Time.deltaTime;
        if (destroyAfterSeconds <= 0)
        {
            Destroy(gameObject);
        }
    }

    private void OnCollisionEnter(Collision c)
    {
        if (c.gameObject.tag == "Player")
        {
            PlayerTank player = c.gameObject.GetComponent<PlayerTank>();
            player.playerTakeDamage(explosionDamage);
            Instantiate(explosionEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }
        if (c.gameObject.tag == "Ground")
        {
            Instantiate(explosionEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }
    }

}