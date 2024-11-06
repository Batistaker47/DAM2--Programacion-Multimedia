using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Goals : MonoBehaviour
{
    public uint playerIndex;
    private void OnTriggerEnter2D(Collider2D collision)
    {
        Ball ball = collision.GetComponent<Ball>();
        if (ball)
        {
            ball.ResetPosition();

            int currentScore = GameManager.instance.GetIndexScore((int)playerIndex);

            GameManager.instance.SetIndexScore((int)playerIndex, currentScore + 1);
        }
    }
}
