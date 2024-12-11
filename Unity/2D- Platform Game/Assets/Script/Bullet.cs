using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    public AudioClip audioClip;
    private Rigidbody2D rb2d;
    private Vector2 direction;
    public float speed;

    // Start is called before the first frame update
    void Start()
    {
        rb2d = GetComponent<Rigidbody2D>();
        Camera.main.GetComponent<AudioSource>().PlayOneShot(audioClip);
    }

    // Update is called once per frame
    void Update()
    {
        rb2d.velocity = direction * speed;
    }

    public void SetDirection(Vector2 givenDirection)
    {
        direction = givenDirection;
    }
    public void DestroyBullet()
    {
        Destroy(gameObject);
    }

    private void OnTriggerEnter2D(Collider2D col)
    {
        JohnMovement john = col.GetComponent<JohnMovement>();
        EnemyScript enemy = col.GetComponent<EnemyScript>();
        if (john != null)
        {
            john.Hit();
        }
         else if (enemy != null)
        {
            enemy.Hit();
        } else
        {
            DestroyBullet();
        }
        
    }
}
