using System.Collections;
using System.Collections.Generic;
using System.Threading;
using UnityEngine;

public class AudioManager : MonoBehaviour
{

    public static AudioManager instance;

    private List<GameObject> activeAudios;

    private void Awake()
    {
        if(!instance)
        {
            instance = this;
            DontDestroyOnLoad(gameObject);
            activeAudios = new List<GameObject>();

        } else
        {
            Destroy(gameObject);
        }
    }

    // Parametro de entrada por defecto en el volume y en el loop, por lo que al llamar al metodo no habria que pasarle esos valores
    // El volumen va de 0 a 1
    // ESTE METODO ES PARA CREAR AUDIO SOURCES EN CÓDIGO, QUE ES LO MISMO QUE CREARLOS EN LA INTERFAZ DE UNITY CON BOTON DERECHO, NEW GAME OBJECT Y AÑADIRLE A PELO TODO EN LOS COMPONENTES
    public AudioSource PlayAudio(AudioClip clip, string objectName, float volume = 1, bool isLoop = false)
    {
        GameObject audioObject = new GameObject(objectName);
        // Con esto coge el game object y le añade el componente audio source (que es el componente que hace que suene el audio)
        AudioSource audioSourceComponent = audioObject.AddComponent<AudioSource>();

        // Asociamos las propiedades
        audioSourceComponent.clip = clip;
        audioSourceComponent.volume = volume;
        audioSourceComponent.loop = isLoop;
        audioSourceComponent.Play();

        if (!isLoop) 
        {
            activeAudios.Add(audioObject);
            // LLAMAMOS A LA COORUTINA
            StartCoroutine(CheckAudio(audioSourceComponent));
        }

        return audioSourceComponent;

    }
    // ESTO ES UNA COORUTINA (ES COMO UN HILO). EJECUTA EL CÓDIGO HASTA QUE LLEGA A UNA LÍNEA
    IEnumerator CheckAudio(AudioSource audioSource) 
    {
        while(audioSource.isPlaying)
        {
            // ESTO ES PARA PARAR LA EJECUCION DURANTE 0.2 SEGUNDOS
            yield return new WaitForSeconds(.2f);
        }
        // QUITAMOS EL AUDIO DE LA LISTA ANTES DE DESTRUIRLO PARA AHORRAR RECURSOS
        activeAudios.Remove(audioSource.gameObject);
        Destroy(audioSource.gameObject);
    }
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
