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
public class CoresFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;     //Gerenciamento do foco de áudio.

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            //Perder o foco de áudio temporariamente
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // O aplicativo recuperou o foco de áudio novamente.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // O aplicativo perdeu o foco de áudio definitivamente.
                releaseMediaPlayer();
            }
        }
    };

    public CoresFragment() {
        // Construtor público vazio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_cores, container, false);

        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Usando ArrayList para salvar os números de um à dez e suas traduções, foi criado uma nova classe PalavrasClass que dá origem a objetos que armazenam duas Strings.

        final ArrayList<PalavrasClass> palavras = new ArrayList<PalavrasClass>();

        // Como o objeto pertencente a PalavrasClass não é habitual como o String temos que criar o novo objeto para passar: Duas Strings, Uma cor e um áudio(raw)


        palavras.add(new PalavrasClass ("Vermelho","Weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        palavras.add(new PalavrasClass ("Verde","Chokokki", R.drawable.color_green, R.raw.color_green));
        palavras.add(new PalavrasClass ("Marrom","Takaakki", R.drawable.color_brown, R.raw.color_brown));
        palavras.add(new PalavrasClass ("Cinza","Topoppi", R.drawable.color_gray, R.raw.color_gray));
        palavras.add(new PalavrasClass ("Preto","Kululli", R.drawable.color_black, R.raw.color_black));
        palavras.add(new PalavrasClass ("Branco","Kelelli", R.drawable.color_white, R.raw.color_white));
        palavras.add(new PalavrasClass ("Amarelo mostarda","Topiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        palavras.add(new PalavrasClass ("Amarelo","Chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));





        PalavraAdapter itemsAdapter = new PalavraAdapter(getActivity(), palavras, R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        //Configurando o onItemClickListener para tocar o áudio de cada item.

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PalavrasClass itensAtual = palavras.get(position);

                releaseMediaPlayer();

                // Requisita o foco de áudio para tocar o áudio
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