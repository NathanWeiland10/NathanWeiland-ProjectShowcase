// This code is used to control and customize all enemies and their aspects, such as their bullet spanwn points and the bombs or bullets they shoot

using System.Collections;
using UnityEngine;

public class Enemy : MonoBehaviour
{

    public float hitPoints;
    public int pointsOnDeath;
    public int spawnChance;
    public float movementSpeed;
    public float movementVariety = 1;
    public bool moveFromLeft;
    public bool moveFromRight;
    public int fallDamageToPlayer;
    public float fallSpeed;
    public float deathTorqueAmount;
    public float minAttackTime;
    public float maxAttakTime;
    public GameObject enemyAttack;
    public Transform enemyAttackSpawnPoint;
    public Transform smokeSpawnPoint;
    public float bombDropForce;
    float attackTime;
    public GameObject hitEffect;
    public GameObject smokeEffect;
    public GameObject deathEffect;
    int smokeSpawn = 0;
    float randomMoveAdd;
    GameObject player;

    public bool ballTurretGunner = false;
    public GameObject turBullet;
    public string turShootSound;
    public Transform ballTurret;
    float barAngle;
    Rigidbody bulletRB;
    public float shotDelay;
    public float bulletForce;
    public float bulletSpread;
    public Transform bulletSpawnPoint;
    bool isDelaying;
    Vector3 targetPosition;
    Vector3 barPosition;

    Rigidbody rb;

    public string[] dropSoundEffects;
    public string[] deathSoundEffects;
    public string[] explodeSoundEffects;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        attackTime = Random.Range(minAttackTime, maxAttakTime);
        randomMoveAdd = Random.Range(1, movementVariety);
        player = GameObject.Find("Player(Clone)");
    }

    void Update()
    { 
        attackTime -= Time.deltaTime;
        if (attackTime <= 0 && hitPoints != 0)
        {
            GameObject bomb = Instantiate(enemyAttack, enemyAttackSpawnPoint.position, enemyAttackSpawnPoint.rotation);
            Rigidbody bombRB = bomb.GetComponent<Rigidbody>();
            bombRB.AddForce(-enemyAttackSpawnPoint.up * bombDropForce, ForceMode.Impulse);
            attackTime = Random.Range(minAttackTime, maxAttakTime);

            int chosenSound = Random.Range(0, dropSoundEffects.Length);
            FindObjectOfType<AudioManager>().Play(dropSoundEffects[chosenSound]);

        }

        if (smokeSpawn == 1)
        {

            int chosenSound = Random.Range(0, deathSoundEffects.Length);
            FindObjectOfType<AudioManager>().Play(deathSoundEffects[chosenSound]);

            Instantiate(smokeEffect, smokeSpawnPoint.position, smokeSpawnPoint.rotation);

            if (player != null) 
            {
                player.GetComponent<PlayerTank>().addPoints(pointsOnDeath);
            }
            smokeSpawn = 2;
        }

        if (hitPoints == 0 && smokeSpawn ==0)
        {
            smokeSpawn++;
        }

        if (ballTurretGunner)
        {
            if (player != null && hitPoints != 0) {

                targetPosition = new Vector2(player.transform.position.x, player.transform.position.y);
                barPosition = new Vector3(ballTurret.transform.position.x, ballTurret.transform.position.y, 0);
                targetPosition.x = targetPosition.x - barPosition.x;
                targetPosition.y = targetPosition.y - barPosition.y;
                barAngle = Mathf.Atan2(targetPosition.y, targetPosition.x) * Mathf.Rad2Deg;
                ballTurret.rotation = Quaternion.Euler(new Vector3(0, 0, barAngle + 90));
                
                if (!isDelaying)
                {
                    StartCoroutine(shoot());
                }
            }
        }

    }

    private void FixedUpdate()
    {
        if (moveFromLeft && hitPoints != 0)
        {
            rb.velocity = Vector3.right * movementSpeed * randomMoveAdd;
        }
        else if (moveFromRight && hitPoints != 0) {
            rb.velocity = Vector3.left * movementSpeed * randomMoveAdd;
        }
        else
        {
            rb.AddForce(Vector3.down * fallSpeed);
            rb.constraints = RigidbodyConstraints.None;
            rb.constraints = RigidbodyConstraints.FreezePositionZ;
            rb.AddRelativeTorque(transform.forward * -deathTorqueAmount);
            rb.AddRelativeTorque(transform.right * -deathTorqueAmount/2);
        }

    }
    public void TakeDamage(float damage)
    {
        hitPoints -= damage;
        if (hitPoints < 0)
        {
            hitPoints = 0;
        }
    }

    public float getCurrentHealth()
    {
        return hitPoints;
    }

    public int getSpawnChance()
    {
        return spawnChance;
    }

    IEnumerator shoot()
    {
        FindObjectOfType<AudioManager>().Play(turShootSound);
        isDelaying = true;
        float recoil = Random.Range(-bulletSpread, bulletSpread);
        bulletSpawnPoint.eulerAngles = new Vector3(bulletSpawnPoint.eulerAngles.x, bulletSpawnPoint.eulerAngles.y, bulletSpawnPoint.eulerAngles.z + recoil);
        GameObject bulletToShoot = Instantiate(turBullet, bulletSpawnPoint.position, bulletSpawnPoint.rotation);
        bulletRB = bulletToShoot.GetComponent<Rigidbody>();
        bulletRB.AddForce((-bulletSpawnPoint.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay);
        isDelaying = false;
        bulletSpawnPoint.eulerAngles = new Vector3(bulletSpawnPoint.eulerAngles.x, bulletSpawnPoint.eulerAngles.y, bulletSpawnPoint.eulerAngles.z - recoil);
    }

    private void OnCollisionEnter(Collision c)
    {
        if (c.gameObject.tag == "Ground")
        {
            int chosenSound = Random.Range(0, explodeSoundEffects.Length);
            FindObjectOfType<AudioManager>().Play(explodeSoundEffects[chosenSound]);

            Instantiate(deathEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }

        if (c.gameObject.tag == "Bullet")
        {
            Instantiate(hitEffect, c.gameObject.transform.position, c.gameObject.transform.rotation);
        }

        if (c.gameObject.tag == "Player Respawner")
        {
            Physics.IgnoreCollision(c.gameObject.GetComponent<Collider>(), GetComponent<Collider>());
        }

        if (c.gameObject.tag == "Player")
        {
            int chosenSound = Random.Range(0, explodeSoundEffects.Length);
            FindObjectOfType<AudioManager>().Play(explodeSoundEffects[chosenSound]);

            PlayerTank player = c.gameObject.GetComponent<PlayerTank>();
            player.playerTakeDamage(fallDamageToPlayer);
            Instantiate(deathEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }

        if (c.gameObject.tag == "Kill Boundary")
        {
            Destroy(gameObject);
        }

        if (c.gameObject.tag == "MiniTank")
        {
            int chosenSound = Random.Range(0, explodeSoundEffects.Length);
            FindObjectOfType<AudioManager>().Play(explodeSoundEffects[chosenSound]);

            Instantiate(deathEffect, transform.position, transform.rotation);
            Destroy(gameObject);
        }

        }
    public void setEnemyDirection(int dir)
    {
        if (dir == 1)
        {
            moveFromLeft = true;
            moveFromRight = false;
        }
        else if (dir == 2)
        {
            moveFromLeft = false;
            moveFromRight = true;
        }
    }
    public bool getDirectionLeft()
    {
        return moveFromLeft;
    }
    public float getMovementSpeed()
    {
        return movementSpeed;
    }

}
