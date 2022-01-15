// This code is used to determine the traits that enemy bombs and bullets have and what actions to take based on collisions

using UnityEngine;

public class EnemyBomb : MonoBehaviour
{
    public float destroyAfterSeconds;
    public GameObject explosionEffect;
    public int explosionDamage;
    public float bulletHealth;

    void Update()
    {
        destroyAfterSeconds -= Time.deltaTime;
        if (destroyAfterSeconds <= 0)
        {
            Destroy(gameObject);
        }
        if (bulletHealth <= 0)
        {
            Instantiate(explosionEffect, transform.position, transform.rotation);
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
        if (c.gameObject.tag == "Bullet")
        {
            bulletHealth -= c.gameObject.GetComponent<Bullet>().getBulletDamage();
        }
        if (c.gameObject.tag == "MiniTank")
        {
            Instantiate(explosionEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }

    }

}