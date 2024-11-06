using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{

    int[] puntuacion;

    // SINGLETON: Variable UNICA en el código y accesible desde cualquier parte
    public static GameManager instance;
    
    // Awake is called before the Start function
    void Awake()
    {
        // SI NO EXISTE EL GAME MANAGER
        if (!instance)
        {
            // ESTABLECEMOS EL NUEVO GAME MANAGER, pero NO ES NECESARIO INICIALIZAR EL OBJETO YA QUE UNITY LO REALIZA POR DETRÁS
            instance = this;
            // ESTE MÉTODO NOS SIRVE PARA QUE NO SE CARGUE LA INFORMACÓN DE LA ESCENA EN LA QUE ESTAMOS
            DontDestroyOnLoad(gameObject);
        } else
        {
            Destroy(gameObject);
        }
    }
    private void Start()
    {
        puntuacion = new int[2];
    }

    public int GetIndexScore(int index)
    {
        return puntuacion[index];
    }

    public void SetIndexScore(int index, int value)
    {
        puntuacion[index] = value;
    }

    public void ChangeScene(string sceneName)
    {
        SceneManager.LoadScene(sceneName);
    }
}
