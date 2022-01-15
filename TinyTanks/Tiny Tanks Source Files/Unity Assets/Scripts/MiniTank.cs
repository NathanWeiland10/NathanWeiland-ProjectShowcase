// This code is used to simulate the minitank and its actions, such as moving, finding enemy targets, and shooting

using System.Collections;
using UnityEngine;

public class MiniTank : MonoBehaviour
{

    public float moveSpeed;
    public GameObject standardBullet;
    public string shootSound;
    bool foundTarget = false;
    public BoxCollider enemyFinder;

    public Transform ballTurret;
    float barAngle;
    public float changeAngleSpeed;
    public float angleRange;

    public Rigidbody miniTankRB;

    Rigidbody bulletRB;
    public float shotDelay;
    public float bulletForce;
    public float bulletSpread;
    GameObject curBullet;
    public Transform bulletSpawnPoint;
    bool isDelaying;

    Vector3 targetPosition;
    Vector3 barPosition;
    GameObject enemyTar;

    UpgradeUIManager UIManager;
    void Start()
    {
        UIManager = FindObjectOfType<UpgradeUIManager>();
        curBullet = standardBullet;
    }
    void Update()
    {
        if (!UIManager.isOnUpgradeScreen()) { 
        if (enemyTar != null)
        {
            if (enemyTar.GetComponent<Enemy>().getCurrentHealth() != 0)
            {
                if (!foundTarget)
                {
                    searchForTarget();
                    enemyFinder.gameObject.SetActive(true);
                }
                else
                {
                    moveToTarget();
                    float xInc;
                    if (enemyTar.GetComponent<Enemy>().getDirectionLeft())
                    {
                        xInc = 2;
                    }
                    else
                    {
                        xInc = -2;
                    }

                    targetPosition = new Vector2(enemyTar.transform.position.x + xInc, enemyTar.transform.position.y);
                    barPosition = new Vector3(ballTurret.transform.position.x, ballTurret.transform.position.y, 0);
                    targetPosition.x = targetPosition.x - barPosition.x;
                    targetPosition.y = targetPosition.y - barPosition.y;
                    barAngle = Mathf.Atan2(targetPosition.y, targetPosition.x) * Mathf.Rad2Deg;
                    ballTurret.rotation = Quaternion.Euler(new Vector3(0, 0, barAngle - 270));

                    if (!isDelaying)
                    {
                        StartCoroutine(shoot());
                    }
                }
            }
            else
            {
                searchForTarget();
            }
        }
        else
        {
            searchForTarget();
        }
    }
    }

    public void searchForTarget()
    {
        barAngle = Mathf.Sin(Time.time * changeAngleSpeed) * angleRange;
        ballTurret.rotation = Quaternion.Euler(new Vector3(0, 0, barAngle - 180));
    }

    public void hasFoundTarget(GameObject enemyTarget)
    {
        foundTarget = true;
        enemyTar = enemyTarget;
    }

    IEnumerator shoot()
    {
        FindObjectOfType<AudioManager>().Play(shootSound);
        isDelaying = true;
        float recoil = Random.Range(-bulletSpread, bulletSpread);
        bulletSpawnPoint.eulerAngles = new Vector3(bulletSpawnPoint.eulerAngles.x, bulletSpawnPoint.eulerAngles.y, bulletSpawnPoint.eulerAngles.z + recoil);
        GameObject bulletToShoot = Instantiate(curBullet, bulletSpawnPoint.position, bulletSpawnPoint.rotation);
        bulletRB = bulletToShoot.GetComponent<Rigidbody>();
        bulletRB.AddForce((-bulletSpawnPoint.up) * bulletForce, ForceMode.Impulse);
        yield return new WaitForSeconds(shotDelay);
        isDelaying = false;
        bulletSpawnPoint.eulerAngles = new Vector3(bulletSpawnPoint.eulerAngles.x, bulletSpawnPoint.eulerAngles.y, bulletSpawnPoint.eulerAngles.z - recoil);
    }

    public void moveToTarget()
    {
        float targetPositionX = enemyTar.transform.position.x;
        float currentPositionX = transform.position.x;

        if (currentPositionX > targetPositionX)
        {
            miniTankRB.AddForce(Vector3.left * moveSpeed);
        }
        else
        {
            miniTankRB.AddForce(Vector3.right * moveSpeed);
        }

    }

}
