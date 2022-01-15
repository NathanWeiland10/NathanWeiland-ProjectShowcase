// This code is used to allow the player tank to instantiate and control a projectile that has their own unique stats

using UnityEngine;
public class Bullet : MonoBehaviour
{

    public float destroyTimer;
    public bool destroyOnCollision;
    public float bulletDamage;
    public GameObject hitEffect;
    public int pointsPerHit;
    GameObject player;
    public bool explodeOnImpact;
    public GameObject fragBullet;

    void Awake()
    {
        player = GameObject.Find("Player(Clone)");
    }

    void Update()
    {
        destroyTimer -= Time.deltaTime;
        if (destroyTimer <= 0)
        {
            Destroy(gameObject);
        }
    }

    private void OnCollisionEnter(Collision c)
    {
        if (c.gameObject.tag == "Enemy")
        {
            Enemy enemy = c.gameObject.GetComponent<Enemy>();
            enemy.TakeDamage(bulletDamage);

            if (enemy.getCurrentHealth() != 0)
            {
                if (player != null)
                {
                    player.GetComponent<PlayerTank>().addPoints(pointsPerHit);
                }

            }

            if (destroyOnCollision && !explodeOnImpact)
            {
                Destroy(gameObject);
            }

            if (destroyOnCollision && explodeOnImpact)
            {
                if (enemy.getCurrentHealth() != 0) {
                    for (int i = 0; i < 20; i++)
                    {
                        Quaternion randRotate = Quaternion.Euler(new Vector3(Random.Range(0, 360), Random.Range(0, 360), Random.Range(0, 360)));
                        Instantiate(fragBullet, transform.position, randRotate);
                    }
                }
                Destroy(gameObject);
            }

        }

        if (c.gameObject.tag == "Kill Boundary")
        {
            Destroy(gameObject);
        }
    }

    public float getBulletDamage()
    {
        return bulletDamage;
    }

}
