/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.exemploappidioma_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creditos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu_creditos option in the app bar overflow menu_creditos
        switch (item.getItemId()) {
            // Responde ao click na opção "Sobre o App" no menu_creditos superior
            case R.id.creditos:
                // Mostra uma toast com o nome do desenvolvedor
                Context context = getApplicationContext();
                CharSequence text = "Desenvolvido por Sobral Apps";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta a MainActivity.java para usar activity_main.xml layout
        setContentView(R.layout.activity_main);

        // Encontre o ViewPager que permitirá ao usuário deslizar entre fragmentos
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        // Crie um adapter que saiba qual fragmento deve ser mostrado na página dependendo de qual opção é selecionada
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());

        // Faz a interação entre adpter e viewPager
        viewPager.setAdapter(adapter);

        // Ache o layout da aba que mostra as abas
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Conecte o layout da aba com o view pager. Isto irá
        //   1. Atualizar o layout da aba quando o view pager for deslizado
        //   2. Atualizar o view pager quando uma aba for selecionada
        //   3. Definir os nomes da aba do layout da aba com os títulos do adapter do view pager
        //      chamando onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

    }
}
