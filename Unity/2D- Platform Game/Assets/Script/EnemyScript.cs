using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyScript : MonoBehaviour
{
    public GameObject John;
    private Animator animator;
    public AudioClip audioClipDeath;
    private float LastShoot;
    public GameObject bulletPrefab;
    public int health = 3;
    private bool IsDead = false;

    // Start is called before the first frame update
    void Start()
    {
        animator = GetComponent<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        // EN CADA FRAME EL ENEMIGO NOS TIENE QUE MIRAR
        // TRANSFORM POSITION ES LA POSICION DEL MALO, Y LA OTRA LA DE JOHN
        // SI HACEMOS LA RESTA, SE OBTIENE EL VECTOR ENTRE JOHN Y EL ENEMIGO

        if (John == null)
        {
            return;
        }
        Vector3 direction = John.transform.position - transform.position;

        if(direction.x >= 0.0f)
        {
            transform.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        } else
        {
            transform.localScale = new Vector3(-1.0f, 1.0f, 1.0f);
        }

        float distance = Mathf.Abs(John.transform.position.x - transform.position.x);

        if (distance < 1.0f && Time.time > LastShoot + 0.75f && !IsDead)
        {
            Shoot();
            LastShoot = Time.time;
        }
    }

    private void Shoot()
    {
        Vector3 direction;
        if (transform.localScale.x == 1.0f)
        {
            direction = Vector3.right;
        }
        else
        {
            direction = Vector3.left;
        }
        GameObject bullet = Instantiate(bulletPrefab, transform.position + direction * 0.1f, Quaternion.identity);
        bullet.GetComponent<Bullet>().SetDirection(direction);
        
    }
    public void Hit()
    {
        health = health - 1;
        if (health == 0)
        {
            IsDead = true;
            animator.SetBool("IsDead", IsDead);
            AudioManager.instance.enabled = true;
            AudioManager.instance.PlayAudio(audioClipDeath, "DeathEnemySound");
            //Destroy(gameObject);
        }
    }

    public void Death()
    {
        Destroy(gameObject);
    }
}
