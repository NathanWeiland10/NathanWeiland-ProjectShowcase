// This code is based on the tutorial by Random Art Attack (https://www.youtube.com/watch?v=8iW2GbWlFWE&t=35s) and is used to create multiple boundaries in the game that are used to respawn the player if necessary or delete off-screen enemies

using UnityEngine;

public class PlayerBoundaries : MonoBehaviour
{

    // Begin Citation:
    // https://www.youtube.com/watch?v=8iW2GbWlFWE&t=35s
    // This code will place two gameobjects that will create a boundary for the player that scales with the current screen size 
    public Camera cam;
    public float boundaryHeight;
    float width;
    float height;
    public GameObject leftBoundaryPrefab;
    public GameObject rightBoundaryPrefab;
    GameObject leftBound;
    GameObject rightBound;
    public GameObject leftBoundPrefab;
    public GameObject rightBoundPrefab;
    GameObject leftKillBoundary;
    GameObject rightKillBoundary;
    public GameObject playerRespawnerLeftPrefab;
    public GameObject playerRespawnerRightPrefab;
    GameObject playerSpawnLeft;
    GameObject playerSpawnRight;
    Vector3 leftBlock = new Vector3(100f, 100f, 100f);
    Vector3 rightBlock = new Vector3(100f, 100f, 100f);
    Vector3 leftKillBound = new Vector3(100f, 100f, 100f);
    Vector3 rightKillBound = new Vector3(100f, 100f, 100f);
    Vector3 leftPlayerSpawn = new Vector3(100f, 100f, 100f);
    Vector3 rightPlayerSpawn = new Vector3(100f, 100f, 100f);

    private void Awake()
    {
        leftBound = Instantiate(leftBoundaryPrefab, leftBlock, Quaternion.identity);
        rightBound = Instantiate(rightBoundaryPrefab, rightBlock, Quaternion.identity);

        leftKillBoundary = Instantiate(leftBoundPrefab, leftBlock, Quaternion.identity);
        rightKillBoundary = Instantiate(rightBoundPrefab, rightBlock, Quaternion.identity);

        playerSpawnLeft = Instantiate(playerRespawnerLeftPrefab, leftBlock, Quaternion.identity);
        playerSpawnRight = Instantiate(playerRespawnerRightPrefab, rightBlock, Quaternion.identity);
    }

    void Update()
    {
        findBoundaries();
        setBounds();
    }

    public void findBoundaries()
    {
        width = 1 / (cam.WorldToViewportPoint(new Vector3(1,1, -52)).x - 0.5f);
        height = 1 / (cam.WorldToViewportPoint(new Vector3(1, 1, -52)).y - 0.5f);
    }

    public void setBounds()
    {
        leftBlock = new Vector3(width / 2, boundaryHeight, -13);
        rightBlock = new Vector3(-width / 2, boundaryHeight, -13);

        leftKillBound = new Vector3(width / 2 + 50, boundaryHeight, -13);
        rightKillBound = new Vector3(-width / 2 - 50, boundaryHeight, -13);

        leftPlayerSpawn = new Vector3(width / 2 + 20, boundaryHeight, -13);
        rightPlayerSpawn = new Vector3(-width / 2 - 20, boundaryHeight, -13);

        leftBound.transform.position = leftBlock;
        rightBound.transform.position = rightBlock;

        leftKillBoundary.transform.position = leftKillBound;
        rightKillBoundary.transform.position = rightKillBound;

        playerSpawnLeft.transform.position = leftPlayerSpawn;
        playerSpawnRight.transform.position = rightPlayerSpawn;
    }
    // End Citation

    public float getHorizontalBoundary()
    {
        return (width / 2);
    }
    public float getVerticalBoundary()
    {
        return (height / 2);
    }

}
