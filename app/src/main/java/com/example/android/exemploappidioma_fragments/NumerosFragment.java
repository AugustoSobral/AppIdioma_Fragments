package com.example.android.exemploappidioma_fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumerosFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;     //Gerenciamento do foco de áudio.

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            //Perder o foco de áudio temporariamente
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);   //Move a mídia para o tempo zero, para que ela não inicia pela metade quando o foco for recuperado
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //  O aplicativo recuperou o foco de áudio novamente.
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //  O aplicativo perdeu o foco de áudio definitivamente.
                releaseMediaPlayer();
            }
        }
    };


    public NumerosFragment() {
        // Construtor público vazio
    }


    //Método que libera a memória que é usada para tocar os arquivos de áudio.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_numeros, container, false);

        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Usando ArrayList para salvar os números de um à dez e suas traduções, foi criado uma nova classe PalavrasClass que dá origem a objetos que armazenam duas Strings e uma imagem.

        final ArrayList<PalavrasClass> palavras = new ArrayList<PalavrasClass>();

        // Como o objeto pertencente a PalavrasClass não é habitual como o String temos que criar o novo objeto antes de passar: Duas Strings, a imagem relativa ao número e o áudio relativo a cada número(raw)
        // PalavrasClass w = new PalavrasClass("Um","Lutti", R.drawable.number_one, R.raw.number_one);
        // palavras.add(w);
        // De uma forma resumida pode-se escrever:

        palavras.add(new PalavrasClass ("Um","Lutti", R.drawable.number_one, R.raw.number_one));
        palavras.add(new PalavrasClass ("Dois","Otiiko", R.drawable.number_two, R.raw.number_two));
        palavras.add(new PalavrasClass ("Três","Tolookosu", R.drawable.number_three, R.raw.number_three));
        palavras.add(new PalavrasClass ("Quatro","Oyyisa", R.drawable.number_four, R.raw.number_four));
        palavras.add(new PalavrasClass ("Cinco","Massokka", R.drawable.number_five, R.raw.number_five));
        palavras.add(new PalavrasClass ("Seis","Temmokka", R.drawable.number_six, R.raw.number_six));
        palavras.add(new PalavrasClass ("Sete","Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        palavras.add(new PalavrasClass ("Oito","Kawinta", R.drawable.number_eight, R.raw.number_eight));
        palavras.add(new PalavrasClass ("Nove","Wo'e", R.drawable.number_nine, R.raw.number_nine));
        palavras.add(new PalavrasClass ("Dez","Na'aacha", R.drawable.number_ten, R.raw.number_ten));



        //A cor category definida no arquivo de cores dá a cor tema da lista no aplicativo
        PalavraAdapter itemsAdapter = new PalavraAdapter(getActivity(), palavras, R.color.category_numbers);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        //Configurando o onItemClickListener para tocar o áudio de cada item.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PalavrasClass itensAtual = palavras.get(position);

                releaseMediaPlayer(); //Libera memória antes de começar um áudio novo, para o caso de vários serem tocados sem o anterior ter terminado.

                //  Requisita o foco de áudio para tocar o áudio
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Usa Stream Music.
                        AudioManager.STREAM_MUSIC,
                        // Request áudio por um período curto de tempo, audios de 1,2 segundos.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //Se o foco de áudio foi requisitado com sucesso:
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Começa a tocar

                    mMediaPlayer = MediaPlayer.create(getActivity(), itensAtual.getAudioId());
                    mMediaPlayer.start();

                    //Método que executa uma ação quando o áudio acaba de tocar (libera memória no caso).
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        return rootView;


    }


    //Quando o app é minimizado, liberamos a memória dos áudios também.
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    //Método que libera a memória que é usada para tocar os arquivos de áudio.

    private void releaseMediaPlayer() {
        // Método que libera a memória usada para gerenciar os áudios
        if (mMediaPlayer != null) {
            // Liberamos os recursos de memória

            mMediaPlayer.release();

            // Definimos mMedia player como nulo para mostrar que os recursos estão liberados no momento
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);  //Abandonar o foco de áudio.
        }
    }

}
