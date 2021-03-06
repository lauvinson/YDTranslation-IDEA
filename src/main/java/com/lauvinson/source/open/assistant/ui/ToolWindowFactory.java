/*
 * The MIT License (MIT)
 * Copyright © 2019 <copyright holders>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject
 * to the following conditions:The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM,DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM,OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * Equivalent description see [http://rem.mit-license.org/]
 */

package com.lauvinson.source.open.assistant.ui;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBColor;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.TimeUnit;

public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory, DumbAware {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ToolWindowPanel toolWindowBuilder = new ToolWindowPanel();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        JPanel toolWindowContent = toolWindowBuilder.createToolWindowPanel();
        Content content = contentFactory.createContent(toolWindowContent, null, false);
        toolWindow.getContentManager().addContent(content);
    }


    /**
     * SearchToolWindowPanel
     *
     * @author created by vinson on 2019/7/2
     */
    static
    public class ToolWindowPanel {


        ToolWindowPanel() {
        }

        JPanel createToolWindowPanel() {
            return new draw_star();
        }

        private void createUIComponents() {

        }
    }

    static class thread_star extends Thread {
        int x0;
        int y0;
        int r0;
        int d0;
        double angle;
        Ellipse2D ellipse2D = new Ellipse2D.Double();
        thread_star(int x,int y,int r,double a)
        {
            x0=x;
            y0=y;
            r0=r;
            d0=x0-760;
            angle=a;
        }
        public void run() {
            double an=angle/3;
            while(true)
            {
                x0=(int) (760+d0*Math.cos(angle));
                y0=(int) (440+d0*Math.sin(angle));
                angle = angle + an / 10;
                ellipse2D.setFrame(x0 - r0, y0 - r0, 2 * r0, 2 * r0);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    static class draw_star extends JPanel {
        public static final int max_x = 1600;
        public static final int min_x = 1000;
        public static final int max_y = 450;
        public static final int min_y = 350;
        private static final Color[] c = {
                JBColor.RED,
                JBColor.BLUE,
                JBColor.WHITE,
                JBColor.BLACK,
                JBColor.GRAY,
                JBColor.LIGHT_GRAY,
                JBColor.DARK_GRAY,
                JBColor.PINK,
                JBColor.ORANGE,
                JBColor.YELLOW,
                JBColor.GREEN,
                JBColor.MAGENTA,
                JBColor.CYAN,
        };
        public static final thread_star[] s = new thread_star[c.length];

        draw_star() {

            for (int i = 0; i < s.length; i++) {
                if (s[i] == null) {
                    s[i] = new thread_star(rand(min_x, max_x), rand(min_y, max_y), rand(25, 40), (Math.PI / rand(25, 40)));
                }
                s[i].start();
            }
        }

        public static int rand(int min, int max) {
            return (int) (Math.random() * (max - min) + min);
        }

        public void paint(Graphics g) {
            g.fillRect(0, 0, getWidth(), getHeight());
            Image offScreenImage = this.createImage(this.getWidth(), this.getHeight());
            Graphics gImage = offScreenImage.getGraphics();
            super.paint(gImage);
            ((Graphics2D) g).setBackground(JBColor.BLACK);
            g.drawImage(offScreenImage, 0, 0, null);
        }

        public void paintComponent(Graphics g0) {
            Graphics2D g = (Graphics2D) (g0);
            for (int i = 0; i < c.length -1; i++) {
                thread_star t = s[i];
                g.setColor(c[i]);
                g.fill(t.ellipse2D);
            }
            repaint();
        }
    }
}