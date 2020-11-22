package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.event.KeyEvent;
import java.util.Random;

public class Player {
    Random r;
    private int initialX, initialY, x, y,
            worldWidth, worldHeight;
    private TETile[][] worldFrame;
    private String path = "";



    Player(int Width, int Height, TETile[][] world, Random random) {
        worldWidth = Width;
        worldHeight = Height;
        worldFrame = world;
        r = random;
    }

    public String getPath() { return path; }

    public TETile[][] addPlayer() {
        for (x = (worldWidth / 8 + r.nextInt(worldWidth / 3)); x < worldWidth; x++) {
            for (y = (worldHeight / 8 + r.nextInt(worldHeight / 3)); y < worldHeight; y++) {
                if (worldFrame[x][y] == Tileset.FLOOR) {
                    break;
                }
            }
            if (y < worldHeight && worldFrame[x][y] == Tileset.FLOOR) {
                break;
            }
        }
        worldFrame[x][y] = Tileset.AVATAR;
        initialX = x;
        initialY = y;
        return worldFrame;
    }


    public TETile[][] move(char input) {
        int factor = 0;
        path += input;
        if (input == 'A' || input == 'a' || input == 'D' || input == 'd') {
            if (input == 'A' || input == 'a') {
                factor= -1;
            } else {
                factor = 1;
            }
            if (worldFrame[x + factor][y] == Tileset.FLOOR) {
                worldFrame[x][y] = Tileset.FLOOR;
                x += factor;
                worldFrame[x][y] = Tileset.AVATAR;
            }
        } else if (input == 'S' || input == 's' || input == 'W' || input == 'w') {
            if (input == 'S' || input == 's') {
                factor = -1;
            } else if (input == 'W' || input == 'w') {
                factor = 1;
            }
            if (worldFrame[x][y + factor] == Tileset.FLOOR) {
                worldFrame[x][y] = Tileset.FLOOR;
                y += factor;
                worldFrame[x][y] = Tileset.AVATAR;
            }
        }
        return worldFrame;
    }
}
