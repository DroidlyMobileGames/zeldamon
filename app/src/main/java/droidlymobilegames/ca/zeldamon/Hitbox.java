package droidlymobilegames.ca.zeldamon;

public class Hitbox {
    public int x;
    public int y;
    public int width;
    public int height;

    // public MyRectangle() {

    //}
    public Hitbox() {
        //this(0, 0, 0, 0);
    }
    public Hitbox(Hitbox r) {
        this(r.x, r.y, r.width, r.height);
    }
    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void grow(int w, int h) {
        this.x -= w;
        this.y -= h;
        this.width += 2 * w;
        this.height += 2 * h;
    }

    public boolean intersects(Hitbox bounds) {
        return this.x >= bounds.x && this.x < bounds.x + bounds.width
                && this.y >= bounds.y && this.y < bounds.y + bounds.height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }
    public boolean intersecting(Hitbox r) {
        int tw = this.width;
        int th = this.height;
        int rw = r.width;
        int rh = r.height;
        if (rw > 0 && rh > 0 && tw > 0 && th > 0) {
            int tx = this.x;
            int ty = this.y;
            int rx = r.x;
            int ry = r.y;
            rw += rx;
            rh += ry;
            tw += tx;
            th += ty;
            return (rw < rx || rw > tx) &&
                    (rh < ry || rh > ty) &&
                    (tw < tx || tw > rx) &&
                    (th < ty || th > ry);
        } else {
            return false;
        }
    }
    public boolean inside(int X, int Y) {
        int w = this.width;
        int h = this.height;
        if ((w | h) < 0) {
            return false;
        } else {
            int x = this.x;
            int y = this.y;
            if (X >= x && Y >= y) {
                w += x;
                h += y;
                return (w < x || w > X) && (h < y || h > Y);
            } else {
                return false;
            }
        }
    }
    public Hitbox intersection(Hitbox r) {
        int tx1 = this.x;
        int ty1 = this.y;
        int rx1 = r.x;
        int ry1 = r.y;
        long tx2 = (long)tx1;
        tx2 += (long)this.width;
        long ty2 = (long)ty1;
        ty2 += (long)this.height;
        long rx2 = (long)rx1;
        rx2 += (long)r.width;
        long ry2 = (long)ry1;
        ry2 += (long)r.height;
        if (tx1 < rx1) {
            tx1 = rx1;
        }

        if (ty1 < ry1) {
            ty1 = ry1;
        }

        if (tx2 > rx2) {
            tx2 = rx2;
        }

        if (ty2 > ry2) {
            ty2 = ry2;
        }

        tx2 -= (long)tx1;
        ty2 -= (long)ty1;
        if (tx2 < -2147483648L) {
            tx2 = -2147483648L;
        }

        if (ty2 < -2147483648L) {
            ty2 = -2147483648L;
        }

        return new Hitbox(tx1, ty1, (int)tx2, (int)ty2);
    }

    public int getCenterX() {
        return (this.x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.y + this.height) / 2;
    }
    public int getX() {
        return (int)this.x;
    }

    public int getY() {
        return (int)this.y;
    }

    public double getWidth() {
        return (double)this.width;
    }

    public double getHeight() {
        return (double)this.height;
    }
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return overlap(rec1[0], rec1[2], rec2[0], rec2[2]) > 0 && overlap(rec1[1], rec1[3], rec2[1], rec2[3]) > 0;
    }
    private int overlap(int left1, int right1, int left2, int right2) {
        return Math.min(right1, right2) >= Math.max(left1, left2) ? Math.min(right1, right2) - Math.max(left1, left2) : 0;
    }
    public boolean isRectangleOverlap2(int[] rec1, int[] rec2) {
        return (Math.min(rec1[2], rec2[2]) > Math.max(rec1[0], rec2[0]) && // width > 0
                Math.min(rec1[3], rec2[3]) > Math.max(rec1[1], rec2[1]));  // height > 0
    }

    public boolean containsballs(int X, int Y, int W, int H) {
        int w = this.width;
        int h = this.height;
        if ((w | h | W | H) < 0) {
            return false;
        } else {
            int x = this.x;
            int y = this.y;
            if (X >= x && Y >= y) {
                w += x;
                W += X;
                if (W <= X) {
                    if (w >= x || W > w) {
                        return false;
                    }
                } else if (w >= x && W > w) {
                    return false;
                }

                h += y;
                H += Y;
                if (H <= Y) {
                    if (h >= y || H > h) {
                        return false;
                    }
                } else if (h >= y && H > h) {
                    return false;
                }

                return true;
            } else {
                return false;
            }
        }
    }
    public Hitbox getBounds() {
        return new Hitbox(this.x, this.y, this.width, this.height);
    }
}