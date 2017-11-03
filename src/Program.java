import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Program extends PApplet {
    private static int MAP_WIDTH = 840;
    private static int MAP_HEIGHT = 600;
    private static int BLOCK_WIDTH = 40;
    private static int BLOCK_HEIGHT = 40;
    private static int ROW = MAP_WIDTH / BLOCK_WIDTH;
    private static int COLUMN = MAP_HEIGHT / BLOCK_HEIGHT;

    private PImage itemImage;
    private PImage stayImage;
    private PImage moveImage;
    private PImage effectImage;
    private PImage blockImage;

    private PImage[] itemImages;
    private PImage[] stayImages;
    private PImage[] moveImages;
    private PImage[] effectImages;
    private PImage[] blockImages;

    private List<Bomb> bombs = new ArrayList<>();

    private Player player2;
    private Bomb bomb;

    private int tick = 0;

    //1->이동속도 아이템
    //2->화력 아이템
    //3->폭탄수 아이템
    //0->안깨지는 블록
    //4->깨지는 블록
    //5->비어있는 곳
    //6->폭탄
    //7->터질때

    private int[][] blocks = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0},
            {0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    @Override
    public void draw() {
        drawMap();
        tick++;
        drawPlayer();
        drawBomb();
        eatItem();

    }

    @Override
    public void setup() {
        background(0);
        createMap();
        itemImage = loadImage("bomberman-items.png");
        stayImage = loadImage("bomberman-stay.png");
        moveImage = loadImage("bomberman-movement.png");
        effectImage = loadImage("bomberman-effect.png");
        blockImage = loadImage("bomberman-block.png");
        cropImage();
        drawMap();

        player2 = new Player(19, 1, 0.02f, 2, 1, 2);

    }

    @Override
    public void settings() {
        size(MAP_WIDTH, MAP_HEIGHT);
    }

    public static void main(String[] args) {
        PApplet.main("Program");
    }

    private void drawMap() {

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                switch (blocks[j][i]) {
                    case 0:
                        image(blockImages[9], i * 40, j * 40);
                        break;
                    case 1:
                        image(itemImages[4], i * 40, j * 40);
                        break;

                    case 2:
                        image(itemImages[0], i * 40, j * 40);
                        break;

                    case 3:
                        image(itemImages[2], i * 40, j * 40);
                        break;

                    case 4:
                        image(blockImages[11], i * 40, j * 40);
                        break;

                    case 5:
                        image(itemImages[10], i * 40, j * 40);
                        break;
                }
            }
        }
    }

    private void cropImage() {
        itemImages = new PImage[20];
        stayImages = new PImage[12];
        moveImages = new PImage[20];
        effectImages = new PImage[45];
        blockImages = new PImage[12];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                itemImages[j * 10 + i] = itemImage.get(24 * i, 24 * j, 24, 24);
                itemImages[j * 10 + i].resize(40, 40);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                stayImages[j * 3 + i] = stayImage.get(20 * i, 32 * j, 20, 32);
                stayImages[j * 3 + i].resize(20, 36);
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                moveImages[j * 5 + i] = moveImage.get(20 * i, 32 * j, 20, 32);
                moveImages[j * 5 + i].resize(20, 36);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                effectImages[j * 9 + i] = effectImage.get(24 * i, 24 * j, 24, 24);
                effectImages[j * 9 + i].resize(40, 40);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                blockImages[j * 3 + i] = blockImage.get(40 * i, 40 * j, 40, 40);
            }
        }
    }

    private void createMap() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (blocks[j][i] == 5) {
                    blocks[j][i] = (int) (Math.random() * 2) + 4;
                }
            }
        }

        blocks[12][1] = blocks[13][1] = blocks[13][2] = blocks[1][19] = blocks[1][18] = blocks[2][19] = 5;
    }

    private void drawPlayer() {
        int x;
        int x2;
        int y;
        int y2;

        if (!keyPressed) {
            switch (player2.getDirection()) {
                case 1:
                    image(stayImages[tick / 10 % 3 + 6], player2.getX() * 40, player2.getY() * 40);
                    break;
                case 2:
                    image(stayImages[tick / 10 % 3], player2.getX() * 40, player2.getY() * 40);
                    break;
                case 3:
                    image(stayImages[tick / 10 % 3 + 9], player2.getX() * 40, player2.getY() * 40);
                    break;
                case 4:
                    image(stayImages[tick / 10 % 3 + 3], player2.getX() * 40, player2.getY() * 40);
                    break;
            }
            return;

        }

        switch (keyCode) {
            case UP:
                player2.setDirection(1);
                x = Math.round(player2.getX());
                x2 = (int) (player2.getX() + 0.5);
                y = player2.getY() % 1 < 0.01 ? (int) player2.getY() : (int) player2.getY() + 1;

                if ((blocks[y - 1][x] == 0 || blocks[y - 1][x] == 4) || (blocks[y - 1][x2] == 0 || blocks[y - 1][x2] == 4)) {
                    image(moveImages[tick / 10 % 5 + 10], player2.getX() * 40, player2.getY() * 40);
                    return;
                }
                image(moveImages[tick / 10 % 5 + 10], player2.getX() * 40, player2.getY() * 40);
                player2.setY(player2.getY() - player2.getV());

                break;

            case DOWN:
                player2.setDirection(2);
                x = Math.round(player2.getX());
                x2 = (int) (player2.getX() + 0.5);
                y = (player2.getY() + 0.9) % 1 < 0.9 ? (int) (player2.getY() + 0.9f) - 1 : (int) (player2.getY() + 0.9f);

                if ((blocks[y + 1][x] == 0 || blocks[y + 1][x] == 4) || (blocks[y + 1][x2] == 0 || blocks[y + 1][x2] == 4)) {
                    image(moveImages[tick / 10 % 5], player2.getX() * 40, player2.getY() * 40);
                    return;
                }
                image(moveImages[tick / 10 % 5], player2.getX() * 40, player2.getY() * 40);
                player2.setY(player2.getY() + player2.getV());

                break;

            case LEFT:
                player2.setDirection(3);
                x = player2.getX() % 1 < 0.01 ? (int) player2.getX() : (int) player2.getX() + 1;
                y = Math.round(player2.getY());
                y2 = (int) (player2.getY() + 0.9f);

                if ((blocks[y][x - 1] == 0 || blocks[y][x - 1] == 4) || (blocks[y2][x - 1] == 0 || blocks[y2][x - 1] == 4)) {
                    image(moveImages[tick / 10 % 5 + 15], player2.getX() * 40, player2.getY() * 40);
                    return;
                }
                image(moveImages[tick / 10 % 5 + 15], player2.getX() * 40, player2.getY() * 40);
                player2.setX(player2.getX() - player2.getV());
                break;

            case RIGHT:
                player2.setDirection(4);
                x = (player2.getX() + 0.5) % 1 < 0.9 ? (int) (player2.getX() + 0.5f) - 1 : (int) (player2.getX() + 0.5f);
                y = Math.round(player2.getY());
                y2 = (int) (player2.getY() + 0.9f);

//                System.out.println(player2.getX() + 0.5);
//                System.out.println(x + " , " + y + " , " + y2);
//
//                System.out.println(blocks[y][x + 1]);
//                System.out.println(blocks[y + 1][x + 1]);

                if ((blocks[y][x + 1] == 0 || blocks[y][x + 1] == 4) || (blocks[y2][x + 1] == 0 || blocks[y2][x + 1] == 4)) {
                    image(moveImages[tick / 10 % 5 + 5], player2.getX() * 40, player2.getY() * 40);
                    return;
                }

                image(moveImages[tick / 10 % 5 + 5], player2.getX() * 40, player2.getY() * 40);
                player2.setX(player2.getX() + player2.getV());


                break;

        }

    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            if (player2.getBombCount() > bombs.size()) {
                System.out.println(player2.getBombCount() + "," + bombs.size());
//                bomb = new Bomb((int) player2.getX(), (int) player2.getY());
                bomb = new Bomb(Math.round(player2.getX()), Math.round(player2.getY()));
                bombs.add(bomb);

            }
        }
    }

    private void drawBomb() {

        for (int i = 0; i < bombs.size(); i++) {
            image(effectImages[tick / 10 % 3], bombs.get(i).getX() * 40, bombs.get(i).getY() * 40);
            Bomb bomb;

            if (System.currentTimeMillis() - bombs.get(i).getTime() > 3400) {
                bombs.remove(bombs.get(i));
                return;
            }
            if (System.currentTimeMillis() - bombs.get(i).getTime() > 3000) {
                bomb = bombs.get(i);

                image(effectImages[(tick / 10 % 3 + 1) * 9], bomb.getX() * 40, bomb.getY() * 40);

                for (int j = 0; j < player2.getBombPower(); j++) {
                    if (blocks[bomb.getY()][bomb.getX() - j - 1] == 0) {
                        break;
                    }
                    if (j == player2.getBombPower() - 1) {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 2], (bomb.getX() - player2.getBombPower()) * 40, bomb.getY() * 40);
                    } else {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 3], (bomb.getX() - j - 1) * 40, bomb.getY() * 40);
                    }
                }

                for (int j = 0; j < player2.getBombPower(); j++) {
                    if (blocks[bomb.getY()][bomb.getX() + j + 1] == 0) {
                        break;
                    }
                    if (j == player2.getBombPower() - 1) {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 4], (bomb.getX() + player2.getBombPower()) * 40, bomb.getY() * 40);
                    } else {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 3], (bomb.getX() + j + 1) * 40, bomb.getY() * 40);
                    }
                }

                for (int j = 0; j < player2.getBombPower(); j++) {
                    if (blocks[bomb.getY() - j - 1][bomb.getX()] == 0) {
                        break;
                    }
                    if (j == player2.getBombPower() - 1) {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 6], bomb.getX() * 40, (bomb.getY() - player2.getBombPower()) * 40);
                    } else {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 5], bomb.getX() * 40, (bomb.getY() - j - 1) * 40);
                    }
                }

                for (int j = 0; j < player2.getBombPower(); j++) {
                    if (blocks[bomb.getY() + j + 1][bomb.getX()] == 0) {
                        break;
                    }
                    if (j == player2.getBombPower() - 1) {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 8], bomb.getX() * 40, (bomb.getY() + player2.getBombPower()) * 40);
                    } else {
                        image(effectImages[(tick / 10 % 3 + 1) * 9 + 5], bomb.getX() * 40, (bomb.getY() + j + 1) * 40);
                    }
                }
                destroyBlock(bomb.getX(), bomb.getY(), player2.getBombPower());

            }
        }
    }

    private void destroyBlock(int x, int y, int power) {
        int[] arr_random = {1, 2, 3, 5};
        for (int i = 1; i <= power; i++) {
            if (y - i > 0) {
                if (blocks[y - i][x] == 4) {
                    blocks[y - i][x] = arr_random[(int) (Math.random() * 4)];
                }
            }
            if (y + i < 15) {
                if (blocks[y + i][x] == 4) {
                    blocks[y + i][x] = arr_random[(int) (Math.random() * 4)];
                }
            }

            if (x + i < 20) {
                if (blocks[y][x + i] == 4) {
                    blocks[y][x + i] = arr_random[(int) (Math.random() * 4)];
                }
            }

            if (x - i > 0) {
                if (blocks[y][x - i] == 4) {
                    blocks[y][x - i] = arr_random[(int) (Math.random() * 4)];
                }
            }
        }
    }

    private void eatItem() {
        if (blocks[(int) player2.getY()][(int) player2.getX()] == 1) {
            if (player2.getV() >= 0.08f) {
                blocks[(int) player2.getY()][(int) player2.getX()] = 5;
                return;
            }
            player2.setV(player2.getV() + 0.02f);
            blocks[(int) player2.getY()][(int) player2.getX()] = 5;
        }

        if (blocks[(int) player2.getY()][(int) player2.getX()] == 2) {
            if (player2.getBombPower() >= 3) {
                blocks[(int) player2.getY()][(int) player2.getX()] = 5;
                return;
            }
            player2.setBombPower(player2.getBombPower() + 1);
            blocks[(int) player2.getY()][(int) player2.getX()] = 5;
        }

        if (blocks[(int) player2.getY()][(int) player2.getX()] == 3) {
            if (player2.getBombCount() >= 3) {
                blocks[(int) player2.getY()][(int) player2.getX()] = 5;
                return;
            }
            player2.setBombCount(player2.getBombCount() + 1);
            blocks[(int) player2.getY()][(int) player2.getX()] = 5;
        }
    }


}
