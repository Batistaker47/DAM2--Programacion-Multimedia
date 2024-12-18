using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

//Monobehaviour nos indica que esto es un componente de Unity
public class StickMovement : MonoBehaviour
{
    public float speed;
    private Rigidbody2D rb2d;
    private float y;
    public string axisName;

    // Start is called before the first frame update.
    // Este método es virtual, lo que significa que lo heredamos de monoBehaviour vacío, y lo rellenaremos con lo que necesitemos
    void Start()
    {
        //Con esto accedemos a las propiedades del componente Rigidbody2D
        rb2d = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame.
    void Update()
    {
        // THIS ONLY WORKS ON PC
        //y = Input.GetAxis(axisName);
        //transform.Translate(new Vector2 (0,y) * speed * Time.deltaTime);
        
        foreach (Touch touch in Input.touches)
        {
            if(touch.phase == TouchPhase.Moved)
            {
                //touch.position
                transform.position = new Vector3(transform.position.x , touch.position.y, transform.position.z);
            }else if (touch.phase == TouchPhase.Ended)
            {
                GetComponent<SpriteRenderer>().color = new Color(Random.Range(0,1f), Random.Range(0,1f),Random.Range(0,1f), 1f);
            }
        }
    }

    private void FixedUpdate()
    {
        //Con esta función interactuamos con el rigidBody
        //rb2d.velocity = new Vector2(0, y) * speed;
    }
}
