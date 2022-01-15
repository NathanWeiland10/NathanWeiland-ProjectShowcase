// This script is used to manage many aspects of the game, such as the pause screen, upgrade screen, game timer, and enemy waves

using UnityEngine;
using TMPro;

public class GameManager : MonoBehaviour
{
    public GameObject train;
    public GameObject pauseMenuCanvas;
    public GameObject pauseMenuMainScreen;
    public GameObject pauseMenuOptionsScreen;
    public GameObject cheatsMenu;

    bool gameIsPaused = false;

    public GameObject deathScreenCanvas;
    public TMP_Text deathTimerText;
    public GameObject mainUICanvas;
    public TMP_Text playerHealthText;
    public TMP_Text playerPointsText;

    public GameObject playerPrefab;
    PlayerTank player;
    public UpgradeUIManager upgradeManager;
    public PlayerBoundaries boundaries;
    float spawnTimer;
    public float maxXDistanceSpawnPoint;
    public float minXDistanceSpawnPoint;
    public float maxYDistanceSpawnPoint;
    public float minYDistanceSpawnPoint;
    float xCoord;
    float yCoord;
    Vector3 testCoord;

    private int currentWave = 0;
    private GameObject[] currentEnemies;
    private float currentMinEnemySpawnTime;
    private float currentMaxEnemySpawnTime;

    public GameObject[] wave1Enemies;
    public float minEnemySpawnTimeWave1;
    public float maxEnemySpawnTimeWave1;

    public GameObject[] wave2Enemies;
    public float minEnemySpawnTimeWave2;
    public float maxEnemySpawnTimeWave2;

    public GameObject[] wave3Enemies;
    public float minEnemySpawnTimeWave3;
    public float maxEnemySpawnTimeWave3;

    public GameObject[] wave4Enemies;
    public float minEnemySpawnTimeWave4;
    public float maxEnemySpawnTimeWave4;

    public GameObject[] finalEnemies;
    public float minEnemySpawnTimeFinal;
    public float maxEnemySpawnTimeFinal;

    // bool playerIsDead;
    bool timerActive = true;

    float minutes = 0f;
    float seconds = 0f;

    public float gameTimer = 0f;
    public TMP_Text gameTimerText;

    float trainTimerSpawner = 0f;

    void Awake()
    {
        pauseMenuCanvas.SetActive(false);
        currentWave = 0;
        spawnTimer = Random.Range(minEnemySpawnTimeWave1, maxEnemySpawnTimeWave1);
        GameObject playerObj = Instantiate(playerPrefab, new Vector3(-5.4f, 1.5f, -13f), Quaternion.identity);
        player = playerObj.GetComponent<PlayerTank>();
    }

    // Update is called once per frame
    void Update()
    {
        trainTimerSpawner -= Time.deltaTime;
        if (trainTimerSpawner <= 0)
        {
            spawnTrain();
            trainTimerSpawner = 60f;
        }

        currentWave = (int)minutes;

        switch (currentWave)
        {
            case 0:
                currentEnemies = wave1Enemies;
                currentMinEnemySpawnTime = minEnemySpawnTimeWave1;
                currentMaxEnemySpawnTime = maxEnemySpawnTimeWave1;
                break;
            case 1:
                currentEnemies = wave2Enemies;
                currentMinEnemySpawnTime = minEnemySpawnTimeWave2;
                currentMaxEnemySpawnTime = maxEnemySpawnTimeWave2;
                break;
            case 2:
                currentEnemies = wave3Enemies;
                currentMinEnemySpawnTime = minEnemySpawnTimeWave3;
                currentMaxEnemySpawnTime = maxEnemySpawnTimeWave3;
                break;
            case 3:
                currentEnemies = wave4Enemies;
                currentMinEnemySpawnTime = minEnemySpawnTimeWave4;
                currentMaxEnemySpawnTime = maxEnemySpawnTimeWave4;
                break;
            default:
                currentEnemies = finalEnemies;
                currentMinEnemySpawnTime = minEnemySpawnTimeFinal;
                currentMaxEnemySpawnTime = maxEnemySpawnTimeFinal;
                break;
        }

        if (timerActive) {
            spawnTimer -= Time.deltaTime;
            if (spawnTimer <= 0)
            {
                spawnEnemy();
            }

            if (!upgradeManager.isOnUpgradeScreen())
            {
                gameTimer += Time.deltaTime;
            }

            minutes = Mathf.FloorToInt(gameTimer / 60);
            seconds = Mathf.FloorToInt(gameTimer % 60);

            gameTimerText.text = "Time Survived: " + string.Format("{0:00}:{1:00}", minutes, seconds);
        }

        if (player.getPlayerCurrentHitPoints() <= 0)
        {
            Die();
        }

        if (upgradeManager.isOnUpgradeScreen())
        {
            player.changePlayerCanShoot(false);
        }

        playerHealthText.text = ("Current HP: " + player.getPlayerCurrentHitPoints());
        playerPointsText.text = ("Current Points: " + player.getCurrentPoints());

        if (Input.GetKeyDown(KeyCode.Escape) && !gameIsPaused && !upgradeManager.isOnUpgradeScreen())
        {
            pauseMenuCanvas.SetActive(true);
            pauseMenuMainScreen.SetActive(true);
            pauseMenuOptionsScreen.SetActive(false);
            cheatsMenu.SetActive(false);
            gameIsPaused = true;
            Time.timeScale = 0f;

            playerHealthText.gameObject.SetActive(false);
            playerPointsText.gameObject.SetActive(false);
            gameTimerText.gameObject.SetActive(false);

        }
        else if (Input.GetKeyDown(KeyCode.Escape) && gameIsPaused && !upgradeManager.isOnUpgradeScreen())
        {
            pauseMenuCanvas.SetActive(false);
            gameIsPaused = false;
            Time.timeScale = 1f;

            playerHealthText.gameObject.SetActive(true);
            playerPointsText.gameObject.SetActive(true);
            gameTimerText.gameObject.SetActive(true);

        }

    }

    public bool isOnPauseScreen()
    {
        return gameIsPaused;
    }

    public void spawnEnemy()
    {
        // Choose a random enemy with a random move direction:
        int randEnemy = Random.Range(0, currentEnemies.Length);
        int randEnemySpawnNumber = Random.Range(1, 101);
        GameObject enemyChosen = currentEnemies[randEnemy];

        if (enemyChosen.GetComponent<Enemy>().getSpawnChance() >= randEnemySpawnNumber)
        {
            int dirChosen = Random.Range(1, 3);
            GameObject spawnedEnemy;

            if (dirChosen == 1)
            {
                xCoord = Random.Range(minXDistanceSpawnPoint + boundaries.getHorizontalBoundary(), maxXDistanceSpawnPoint + boundaries.getHorizontalBoundary());
                yCoord = Random.Range(minYDistanceSpawnPoint + boundaries.getVerticalBoundary(), maxYDistanceSpawnPoint + boundaries.getVerticalBoundary());
                testCoord = new Vector3(-xCoord, yCoord, -12);
                spawnedEnemy = Instantiate(enemyChosen, testCoord, Quaternion.identity);
                spawnedEnemy.GetComponent<Enemy>().setEnemyDirection(dirChosen);
            }
            else if (dirChosen == 2)
            {
                xCoord = Random.Range(minXDistanceSpawnPoint + boundaries.getHorizontalBoundary(), maxXDistanceSpawnPoint + boundaries.getHorizontalBoundary());
                yCoord = Random.Range(minYDistanceSpawnPoint + boundaries.getVerticalBoundary(), maxYDistanceSpawnPoint + boundaries.getVerticalBoundary());
                testCoord = new Vector3(xCoord, yCoord, -12);
                spawnedEnemy = Instantiate(enemyChosen, testCoord, Quaternion.identity);
                spawnedEnemy.transform.eulerAngles = new Vector3(spawnedEnemy.transform.eulerAngles.x, spawnedEnemy.transform.eulerAngles.y + 180, spawnedEnemy.transform.eulerAngles.z);
                spawnedEnemy.GetComponent<Enemy>().setEnemyDirection(dirChosen);
            }
            spawnTimer = Random.Range(currentMinEnemySpawnTime, currentMaxEnemySpawnTime);
        }
        else
        {
            spawnTimer = 0f;
        }

    }
    public float getDeathMinutes()
    {
        return minutes;
    }
    public float getDeathSeconds()
    {
        return seconds;
    }
    public void Die()
    {
        mainUICanvas.SetActive(false);
        deathScreenCanvas.SetActive(true);
        deathTimerText.text = "YOU SURVIVED FOR: " + string.Format("{0:00}:{1:00}", minutes, seconds);
        timerActive = false;
    }
    public void spawnTrain()
    {
        int trainDirChosen = Random.Range(1, 3);
        GameObject spawnedTrain;
        if (trainDirChosen == 1)
        {
            float trainXCoord = boundaries.getHorizontalBoundary() + 50f;
            float trainYCoord = 14f;
            testCoord = new Vector3(-trainXCoord, trainYCoord, -25);
            spawnedTrain = Instantiate(train, testCoord, Quaternion.identity);
            spawnedTrain.GetComponent<TrainMovement>().setTrainDirection(trainDirChosen);
        }
        else
        {
            float trainXCoord = boundaries.getHorizontalBoundary() + 50f;
            float trainYCoord = 14f;
            testCoord = new Vector3(trainXCoord, trainYCoord, 26);
            spawnedTrain = Instantiate(train, testCoord, Quaternion.identity);
            spawnedTrain.transform.eulerAngles = new Vector3(spawnedTrain.transform.eulerAngles.x, spawnedTrain.transform.eulerAngles.y + 180, spawnedTrain.transform.eulerAngles.z);
            spawnedTrain.GetComponent<TrainMovement>().setTrainDirection(trainDirChosen);
        }
    }

}