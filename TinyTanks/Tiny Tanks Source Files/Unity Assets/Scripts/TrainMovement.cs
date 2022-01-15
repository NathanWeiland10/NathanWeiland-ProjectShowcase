// This code is used to simulate the movement of the train that spawns in the game after every minute

using UnityEngine;

public class TrainMovement : MonoBehaviour
{
    public float destroyAfterSeconds;
    public Rigidbody trainRB;
    public float trainSpeed;
    bool moveFromLeft;
    bool moveFromRight;

    void Update()
    {
        if (moveFromLeft)
        {
            trainRB.velocity = Vector3.right * trainSpeed;
        }
        else if (moveFromRight)
        {
            trainRB.velocity = Vector3.right * -trainSpeed;
        }

        destroyAfterSeconds -= Time.deltaTime;
        if (destroyAfterSeconds <= 0)
        {
            Destroy(gameObject);
        }

    }

    public void setTrainDirection(int dir)
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

}
