/*
 * Copyright 2022 Manel E. Tarazona Garcia - mantargar@gmail.com
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
package org.japo.java.app;

import java.util.Properties;

import org.japo.java.libraries.UtilesConfig;

/**
 *
 * @author Manel E. Tarazona Garcia - mantargar@gmail.com
 */
public final class App {

    // Propiedades del Sistema
    private final Properties prp;

    public App(Properties prp) {
        this.prp = prp;
    }

    // Logica de Negocio
    public final void launchApp() {
        
        String alu = prp.getProperty(UtilesConfig.PRP_ALU);
        String exp = prp.getProperty(UtilesConfig.PRP_EXP);
        String cic = prp.getProperty(UtilesConfig.PRP_CIC);
        String cur = prp.getProperty(UtilesConfig.PRP_CUR);

        String[] modulos = prp.getProperty(UtilesConfig.PRP_MOD).split("-");

        double[][] notas = new double[3][modulos.length];
        for (int i = 1; i <= 3; i++) {
            String[] notasModulo = prp.getProperty("ev" + i).split("-");
            for (int j = 0; j < notasModulo.length; j++) {
                notas[i - 1][j] = Double.parseDouble(notasModulo[j]);
            }
        }

        double[] notasFinales = new double[modulos.length];
        for (int i = 0; i < modulos.length; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += notas[j][i];
            }
            notasFinales[i] = sum / 3;

        }
        // Propiedades Pantalla EJEMPLO IMPORTAR/EXPORTAR
        System.out.printf("INFORME CURRICULAR - Ciclo: %s - Curso: %s%n", cic, cur);
        System.out.println("=====================================================");
        System.out.printf("Alumno .....: %s%n", alu);
        System.out.printf("Expediente .: %s%n", exp);
        System.out.println("-----------------------------------------------------");
        System.out.println("Módulo                    1EV   2EV   3EV  FINAL");
        System.out.println("---------                ----- ----- ----- -----");
        int susp = 0;
        for (int i = 0; i < modulos.length; i++) {
            System.out.printf("%-9s  ", modulos[i]);
            for (int j = 0; j < 3; j++) {
                System.out.printf("%5s ", notas[j][i]);                
            }
            double notaFin = notasFinales[i];
            System.out.printf("%5s  ", notaFin);
            if (notaFin < 5.0){
                System.out.println("- SUSPENDIDO");
                susp++;
            } else {
                System.out.println("- APROBADO");
            }            
        }
        System.out.println("---");
        System.out.println("Atención a los padres: ");
        System.out.printf("Su hijo/a tiene %d módulo/s suspendido/s ", susp);
    }
}
