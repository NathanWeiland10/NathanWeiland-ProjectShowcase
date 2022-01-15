// This code will take in a transform and set this transform to another transform

using UnityEngine;

public class Follower : MonoBehaviour
{
    public Transform target;

    void Update()
    {
        transform.position = target.position;
    }
}
