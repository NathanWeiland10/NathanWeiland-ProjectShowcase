// This code is used to simulate all of the functions of the player controlled tank, such as shooting, moving, and different types of barrels

using System.Collections;
using UnityEngine;

public class PlayerTank : MonoBehaviour
{
    public GameObject deathEffect;
    public int maxHitPoints;
    int currentHitPoints;
    public int currentPoints;
    public float tankSpeed;
    public float bulletForce;
    public float shotDelay;
    bool isDelaying;
    public float bulletSpread;
    public string normalShotSound;
    Rigidbody tankRB;
    Rigidbody bulletRB;
    bool canShoot;
    public Transform standardBulletSpawnPoint;
    public Transform doubleBulletSpawnPoint1;
    public Transform doubleBulletSpawnPoint2;
    public Transform splitSpawn1;
    public Transform splitSpawn2;
    public Transform splitSpawn3;
    public Transform splitSpawn4;
    public Transform minigunSpawn;
    public GameObject normBullet;
    GameObject curBullet;

    UpgradeUIManager UIManager;
    GameManager gameManager;

    Vector3 mousePos;
    Vector3 barPos;
    public Transform target;
    float barAngle;

    public GameObject standardBarrel;
    public GameObject doubleBarrel1;
    public GameObject doubleBarrel2;
    public GameObject split1;
    public GameObject split2;
    public GameObject split3;
    public GameObject split4;
    public GameObject minigun;
    public int barType = 1; // 1 = standard, 2 = double, 3 split, 4 minigun

    void Awake()
    {
        gameManager = FindObjectOfType<GameManager>();
        UIManager = FindObjectOfType<UpgradeUIManager>();
        isDelaying = false;
        currentHitPoints = maxHitPoints;
        tankRB = GetComponent<Rigidbody>();
        curBullet = normBullet;
    }

    void Update()
    {
        // Begin Citation:
        // From: https://answers.unity.com/questions/10615/rotate-objectweapon-towards-mouse-cursor-2d.html
        // This script is used to point the barrel of the tank to the player's mouse:
        mousePos = Input.mousePosition;
        barPos = Camera.main.WorldToScreenPoint(target.position);
        mousePos.x = mousePos.x - barPos.x;
        mousePos.y = mousePos.y - barPos.y;
        barAngle = Mathf.Atan2(mousePos.y, mousePos.x) * Mathf.Rad2Deg;
        target.rotation = Quaternion.Euler(new Vector3(0, 0, barAngle - 90)); // 270
        // End Citation

        switch (barType)
        {
            case 1:
                standardBarrel.SetActive(true);
                doubleBarrel1.SetActive(false);
                doubleBarrel2.SetActive(false);
                split1.SetActive(false);
                split2.SetActive(false);
                split3.SetActive(false);
                split4.SetActive(false);
                minigun.SetActive(false);
                break;
            case 2:
                standardBarrel.SetActive(false);
                doubleBarrel1.SetActive(true);
                doubleBarrel2.SetActive(true);
                split1.SetActive(false);
                split2.SetActive(false);
                split3.SetActive(false);
                split4.SetActive(false);
                minigun.SetActive(false);
                break;
            case 3:
                standardBarrel.SetActive(false);
                doubleBarrel1.SetActive(false);
                doubleBarrel2.SetActive(false);
                split1.SetActive(true);
                split2.SetActive(true);
                split3.SetActive(true);
                split4.SetActive(true);
                minigun.SetActive(false);
                break;
            case 4:
                standardBarrel.SetActive(false);
                doubleBarrel1.SetActive(false);
                doubleBarrel2.SetActive(false);
                split1.SetActive(false);
                split2.SetActive(false);
                split3.SetActive(false);
                split4.SetActive(false);
                minigun.SetActive(true);
                break;
        }

        if ((barAngle <= -90 && barAngle >= -150) || (barAngle <= -30 && barAngle >= -90))
        {
            canShoot = false;
        }
        else
        {
            canShoot = true;
        }

        if (UIManager.isOnUpgradeScreen() || gameManager.isOnPauseScreen())
        {
            canShoot = false;
        }

            if (Input.GetMouseButton(0) && canShoot && !isDelaying && barType == 1)
            {
                StartCoroutine(standardShoot());
            } else if (Input.GetMouseButton(0) && canShoot && !isDelaying && barType == 2)
            {
                StartCoroutine(doubleShoot());
            } else if (Input.GetMouseButton(0) && canShoot && !isDelaying && barType == 3)
            {
                StartCoroutine(splitShoot());
            } else if (Input.GetMouseButton(0) && canShoot && !isDelaying && barType == 4)
            {
                StartCoroutine(minigunShoot());
            }

        if (currentHitPoints <= 0)
        {
            Die();
        }

        // If the player manages to move far from the play area, teleport the player back to the play area
        if (transform.position.x > 999 || transform.position.x < -999)
        {
            transform.position = new Vector3(-5.4f, 3f, -13f);
        }
        if (transform.position.y > 999 || transform.position.y < -999)
        {
            transform.position = new Vector3(-5.4f, 3f, -13f);
        }
        if (transform.position.z > 999 || transform.position.z < -999)
        {
            transform.position = new Vector3(-5.4f, 3f, -13f);
        }

    }


    private void FixedUpdate()
    {
        if (Input.GetKey(KeyCode.A) || Input.GetKey(KeyCode.LeftArrow))
        {
            tankRB.AddForce(Vector3.left * tankSpeed);
        }
        if (Input.GetKey(KeyCode.D) || Input.GetKey(KeyCode.RightArrow))
        {
            tankRB.AddForce(Vector3.right * tankSpeed);
        }
    }

    IEnumerator standardShoot()
    {
        FindObjectOfType<AudioManager>().Play(normalShotSound);
        isDelaying = true;
        float recoil = Random.Range(-bulletSpread, bulletSpread);
        standardBulletSpawnPoint.eulerAngles = new Vector3(standardBulletSpawnPoint.eulerAngles.x, standardBulletSpawnPoint.eulerAngles.y, standardBulletSpawnPoint.eulerAngles.z + recoil);
        GameObject bulletToShoot = Instantiate(curBullet, standardBulletSpawnPoint.position, standardBulletSpawnPoint.rotation);
        bulletRB = bulletToShoot.GetComponent<Rigidbody>();
        bulletRB.AddForce((-standardBulletSpawnPoint.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay);
        isDelaying = false;
        standardBulletSpawnPoint.eulerAngles = new Vector3(standardBulletSpawnPoint.eulerAngles.x, standardBulletSpawnPoint.eulerAngles.y, standardBulletSpawnPoint.eulerAngles.z - recoil);
    }

    IEnumerator doubleShoot()
    {
        FindObjectOfType<AudioManager>().Play(normalShotSound);
        isDelaying = true;
        float recoil1 = Random.Range(-bulletSpread, bulletSpread);
        float recoil2 = Random.Range(-bulletSpread, bulletSpread);
        doubleBulletSpawnPoint1.eulerAngles = new Vector3(doubleBulletSpawnPoint1.eulerAngles.x, doubleBulletSpawnPoint1.eulerAngles.y, doubleBulletSpawnPoint1.eulerAngles.z + recoil1);
        doubleBulletSpawnPoint2.eulerAngles = new Vector3(doubleBulletSpawnPoint2.eulerAngles.x, doubleBulletSpawnPoint2.eulerAngles.y, doubleBulletSpawnPoint2.eulerAngles.z + recoil2);
        GameObject bulletToShoot1 = Instantiate(curBullet, doubleBulletSpawnPoint1.position, doubleBulletSpawnPoint1.rotation);
        GameObject bulletToShoot2 = Instantiate(curBullet, doubleBulletSpawnPoint2.position, doubleBulletSpawnPoint2.rotation);
        Rigidbody bulletRB1 = bulletToShoot1.GetComponent<Rigidbody>();
        Rigidbody bulletRB2 = bulletToShoot2.GetComponent<Rigidbody>();
        bulletRB1.AddForce((-doubleBulletSpawnPoint1.up) * bulletForce, ForceMode.Impulse);
        bulletRB2.AddForce((-doubleBulletSpawnPoint2.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay);
        isDelaying = false;
        doubleBulletSpawnPoint1.eulerAngles = new Vector3(doubleBulletSpawnPoint1.eulerAngles.x, doubleBulletSpawnPoint1.eulerAngles.y, doubleBulletSpawnPoint1.eulerAngles.z - recoil1);
        doubleBulletSpawnPoint2.eulerAngles = new Vector3(doubleBulletSpawnPoint2.eulerAngles.x, doubleBulletSpawnPoint2.eulerAngles.y, doubleBulletSpawnPoint2.eulerAngles.z - recoil2) ;
    }

    IEnumerator splitShoot()
    {
        FindObjectOfType<AudioManager>().Play(normalShotSound);
        isDelaying = true;
        float recoil1 = Random.Range(-bulletSpread, bulletSpread);
        float recoil2 = Random.Range(-bulletSpread, bulletSpread);
        float recoil3 = Random.Range(-bulletSpread, bulletSpread);
        float recoil4 = Random.Range(-bulletSpread, bulletSpread);
        splitSpawn1.eulerAngles = new Vector3(splitSpawn1.eulerAngles.x, splitSpawn1.eulerAngles.y, splitSpawn1.eulerAngles.z + recoil1);
        splitSpawn2.eulerAngles = new Vector3(splitSpawn2.eulerAngles.x, splitSpawn2.eulerAngles.y, splitSpawn2.eulerAngles.z + recoil2);
        splitSpawn3.eulerAngles = new Vector3(splitSpawn3.eulerAngles.x, splitSpawn3.eulerAngles.y, splitSpawn3.eulerAngles.z + recoil3);
        splitSpawn4.eulerAngles = new Vector3(splitSpawn4.eulerAngles.x, splitSpawn4.eulerAngles.y, splitSpawn4.eulerAngles.z + recoil4);
        GameObject bulletToShoot1 = Instantiate(curBullet, splitSpawn1.position, splitSpawn1.rotation);
        GameObject bulletToShoot2 = Instantiate(curBullet, splitSpawn2.position, splitSpawn2.rotation);
        GameObject bulletToShoot3 = Instantiate(curBullet, splitSpawn3.position, splitSpawn3.rotation);
        GameObject bulletToShoot4 = Instantiate(curBullet, splitSpawn4.position, splitSpawn4.rotation);
        Rigidbody bulletRB1 = bulletToShoot1.GetComponent<Rigidbody>();
        Rigidbody bulletRB2 = bulletToShoot2.GetComponent<Rigidbody>();
        Rigidbody bulletRB3 = bulletToShoot3.GetComponent<Rigidbody>();
        Rigidbody bulletRB4 = bulletToShoot4.GetComponent<Rigidbody>();
        bulletRB1.AddForce((-splitSpawn1.up) * bulletForce, ForceMode.Impulse);
        bulletRB2.AddForce((-splitSpawn2.up) * bulletForce, ForceMode.Impulse);
        bulletRB3.AddForce((-splitSpawn3.up) * bulletForce, ForceMode.Impulse);
        bulletRB4.AddForce((-splitSpawn4.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay);
        isDelaying = false;
        splitSpawn1.eulerAngles = new Vector3(splitSpawn1.eulerAngles.x, splitSpawn1.eulerAngles.y, splitSpawn1.eulerAngles.z - recoil1);
        splitSpawn2.eulerAngles = new Vector3(splitSpawn2.eulerAngles.x, splitSpawn2.eulerAngles.y, splitSpawn2.eulerAngles.z - recoil2);
        splitSpawn3.eulerAngles = new Vector3(splitSpawn3.eulerAngles.x, splitSpawn3.eulerAngles.y, splitSpawn3.eulerAngles.z - recoil3);
        splitSpawn4.eulerAngles = new Vector3(splitSpawn4.eulerAngles.x, splitSpawn4.eulerAngles.y, splitSpawn4.eulerAngles.z - recoil4);
    }

    IEnumerator minigunShoot()
    {
        FindObjectOfType<AudioManager>().Play(normalShotSound);
        isDelaying = true;
        float recoil = Random.Range(-bulletSpread, bulletSpread);
        minigunSpawn.eulerAngles = new Vector3(minigunSpawn.eulerAngles.x, minigunSpawn.eulerAngles.y, minigunSpawn.eulerAngles.z + recoil);
        GameObject bulletToShoot = Instantiate(curBullet, minigunSpawn.position, minigunSpawn.rotation);
        bulletRB = bulletToShoot.GetComponent<Rigidbody>();
        bulletRB.AddForce((-minigunSpawn.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay/3);
        isDelaying = false;
        minigunSpawn.eulerAngles = new Vector3(minigunSpawn.eulerAngles.x, minigunSpawn.eulerAngles.y, minigunSpawn.eulerAngles.z - recoil);
    }

    private void OnCollisionEnter(Collision c)
    {
        if (c.gameObject.tag == "Player Respawner")
        {
            transform.position = new Vector3(0f, 0.6f, -13f);
        }
    }

    public void Die()
    {
        FindObjectOfType<AudioManager>().Play("PlayerDeath");
        Instantiate(deathEffect, transform.position, transform.rotation);
        Destroy(gameObject);
    }

    public float getPlayerCurrentHitPoints()
    {
        return currentHitPoints;
    }

    public void playerTakeDamage(int damage)
    {
        currentHitPoints -= damage;
        FindObjectOfType<AudioManager>().Play("PlayerHit");
        if (currentHitPoints < 0)
        {
            currentHitPoints = 0;
        }
    }

    public int getCurrentPoints()
    {
        return currentPoints;
    }

    public void addPoints(int points)
    {
        currentPoints += points;
    }

    public void removePoints(int points)
    {
        currentPoints -= points;
    }

    public void reduceShotDelay(float amount)
    {
        shotDelay -= amount;
    }
    public void increaseBulletSpeed(float amount)
    {
        bulletForce += amount;
    }
    public void reduceBulletSpread(float amount)
    {
        bulletSpread -= amount;
    }
    public void increaseTankSpeed(float amount)
    {
        tankSpeed += amount;
    }

    public void changePlayerCanShoot(bool b)
    {
        canShoot = b;
    }

    public void changeBarrel(int bar)
    {
        barType = bar;
        mousePos = Input.mousePosition;
        barPos = Camera.main.WorldToScreenPoint(target.position);
        mousePos.x = mousePos.x - barPos.x;
        mousePos.y = mousePos.y - barPos.y;
        barAngle = Mathf.Atan2(mousePos.y, mousePos.x) * Mathf.Rad2Deg;
        target.rotation = Quaternion.Euler(new Vector3(0, 0, barAngle - 270));
    }

}