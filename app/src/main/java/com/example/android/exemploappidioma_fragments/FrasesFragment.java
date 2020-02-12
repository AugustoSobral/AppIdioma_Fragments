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
public class FrasesFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;     //Gerenciamento do foco de áudio.

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            //Perder o foco de áudio temporariamente
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // O aplicativo recuperou o foco de áudio novamente.
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // O aplicativo perdeu o foco de áudio definitivamente.
                releaseMediaPlayer();
            }
        }
    };

    public FrasesFragment() {
        // Construtor público vazio
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_frases, container, false);

        mAudioManager =(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Usando ArrayList para salvar os números de um à dez e suas traduções, foi criado uma nova classe PalavrasClass que dá origem a objetos que armazenam duas Strings.

        final ArrayList<PalavrasClass> palavras = new ArrayList<PalavrasClass>();



        palavras.add(new PalavrasClass ("Onde você vai?","Minto wuksus", R.raw.phrase_where_are_you_going));
        palavras.add(new PalavrasClass ("Qual seu nome?","Tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        palavras.add(new PalavrasClass ("Meu nome é...","oyaaset...", R.raw.phrase_my_name_is));
        palavras.add(new PalavrasClass ("Como está se sentindo?","Michәksәs?", R.raw.phrase_how_are_you_feeling));
        palavras.add(new PalavrasClass ("Estou me sentindo bem.","Kuchi achit", R.raw.phrase_im_feeling_good));
        palavras.add(new PalavrasClass ("Você vem?","әәnәs'aa?", R.raw.phrase_are_you_coming));
        palavras.add(new PalavrasClass ("Sim, eu vou.","hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        palavras.add(new PalavrasClass ("Estou chegando.","әәnәm", R.raw.phrase_im_coming));
        palavras.add(new PalavrasClass ("Vamos lá.","Yoowutis", R.raw.phrase_lets_go));
        palavras.add(new PalavrasClass ("Venha aqui.","әnni'nem", R.raw.phrase_come_here));




        PalavraAdapter itemsAdapter = new PalavraAdapter(getActivity(), palavras, R.color.category_phrases);

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
                        //  Usa Stream Music.
                        AudioManager.STREAM_MUSIC,
                        // Request áudio por um período curto de tempo, audios de 1,2 segundos.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //Se o foco de áudio foi requisitado com sucesso:
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //  Começa a tocar

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
