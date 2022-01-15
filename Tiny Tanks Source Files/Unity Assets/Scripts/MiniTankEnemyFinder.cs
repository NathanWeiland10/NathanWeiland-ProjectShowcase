// This code is used to send messages to the minitank when a collider has encountered an enemy so the minitank knows where to move and shoot

using UnityEngine;

public class MiniTankEnemyFinder : MonoBehaviour
{

    public MiniTank miniTank;
    public BoxCollider enemyFinderCollider;
    GameObject enemyObject;
    float enemyHealth;

    private void OnTriggerEnter(Collider c)
    {
        if (c.gameObject.tag == "Enemy")
        {
            if (c.gameObject != null) {

                enemyObject = c.gameObject;
                Enemy enemy = enemyObject.GetComponent<Enemy>();
                enemyHealth = enemy.getCurrentHealth();

                if (enemyHealth != 0)
                {
                    miniTank.hasFoundTarget(enemyObject);
                }
                else
                {
                    miniTank.searchForTarget();
                }
            }
        }

    }

}
