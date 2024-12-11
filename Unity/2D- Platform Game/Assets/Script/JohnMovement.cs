using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class JohnMovement : MonoBehaviour
{
    public AudioClip audioClipJump;
    public AudioClip audioClipDeath;
    public GameObject bulletPrefab;
    private Rigidbody2D rb2d;
    private Animator animator;
    public float speed;
    public float jumpforce;
    private float x;
    public string axisName;
    private bool isGrounded;
    private float LastShoot;
    public int health = 100;

    // Start is called before the first frame update
    void Start()
    {
        rb2d = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        // MOVEMOS A RAMBO
       x = Input.GetAxis(axisName);
       transform.Translate(new Vector2(x, 0) * speed * Time.deltaTime);

        // LO PONEMOS A MIRAR A LA IZQUIERDA O A LA DERECHA EN FUNCION DE LA TECLA DE DESPLAZAMIENTO
        if (x < 0.0f)
        {
            transform.localScale = new Vector3(-1.0f, 1.0f, 1.0f);
        }
        else if (x > 0.0f)
        {
            transform.localScale = new Vector3(1.0f, 1.0f, 1.0f);
        }

        // FIJAMOS EL CONDICIONANTE DE LA ANIMACIÓN DE CORRER SÓLO SI EL VALOR DE X ES DISTINTO DE 0
        animator.SetBool("isRunning", x != 0.0f);

        // CONTROL DE ERRORES PARA QUE RAMBO SÓLO PUEDA SALTAR UNA VEZ CON LA W
        Debug.DrawRay(transform.position, Vector3.down * 0.1f, Color.red);
        if(Physics2D.Raycast(transform.position, Vector3.down, 0.1f))
        {
            isGrounded = true;
        } else
        {
            isGrounded= false;
        }
        // ESTO ES LA CLAVE DEL FLAPPY BIRDS!!
        // SI AL PULSAR LA W Y RAMBO ESTÁ EN EL SUELO
        if (Input.GetKeyDown(KeyCode.W) && isGrounded)
        {
            Jump();
        }

        if(Input.GetKeyDown(KeyCode.Space) && Time.time > LastShoot + 0.25f) 
        {
            Shoot();
            LastShoot = Time.time;
        }
    }

    private void Jump()
    {
        rb2d.AddForce(Vector2.up * jumpforce);
        AudioManager.instance.enabled = true;
        AudioManager.instance.PlayAudio(audioClipJump, "JumpSound");
    }

    private void Shoot()
    {
        Vector3 direction;
        if (transform.localScale.x == 1.0f)
        {
            direction = Vector3.right;
        } else
        {
            direction = Vector3.left;
        }
        GameObject bullet = Instantiate(bulletPrefab, transform.position + direction * 0.15f, Quaternion.identity);
        bullet.GetComponent<Bullet>().SetDirection(direction);
    }

    public void Hit()
    {
        health -= 1;
        //Debug.Log(((uint)health));
        if (health == 0)
        {
            AudioManager.instance.enabled = true;
            AudioManager.instance.PlayAudio(audioClipDeath, "DeathSound");
            Destroy(gameObject);
        }
    }

    private void FixedUpdate()
    {

        //rb2d.velocity = new Vector2(Horizontal, rb2d.velocity.y);
    }
}
