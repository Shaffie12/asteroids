package util;

public final class Vector2D
{
    public double x;
    public double y;

    public Vector2D(){}

    //set variables constructors
    public Vector2D(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    //copy constructors
    public Vector2D(Vector2D v)
    {
        this.x=v.x;
        this.y=v.y;
    }

    //methods

    public Vector2D set(double x, double y)
    {
        this.x=x;
        this.y=y;
        return this;
    }

    public Vector2D set(Vector2D v)
    {
        this.x=v.x;
        this.y=v.y;
        return this;
    }

    public boolean equals(Object o) //throws invalid arg?
    {
        if(o.getClass()==getClass())
        {
            Vector2D vec = (Vector2D) o;
            if (vec.x == this.x && vec.y == this.y) {
                return true;
            }
        }
        return false;
    }

    public String toString()
    {
        String s="";
        s+=this.x + " , "+ this.y;
        return s;
    }

    public double mag()
    {
        return Math.hypot(this.x,this.y);

    }

    public double angle()
    {
        return Math.atan2(y,x);
    }

    public double angle(Vector2D other)
    {
        double a = this.angle();
        double b = other.angle();

        double result = b-a;

        if(result < -Math.PI)
        {
            result+=2*Math.PI;
        }
        if(result > Math.PI)
        {
            result-=2*Math.PI;
        }

        return result;
    }

    public Vector2D add(Vector2D v)
    {
        this.x+=v.x;
        this.y+=v.y;
        return this;
    }

    public Vector2D add(double x, double y)
    {
        this.x+=x;
        this.y+=y;
        return this;
    }

    public Vector2D addScaled(Vector2D v, double fact)
    {
        Vector2D scaledV = new Vector2D(v.x*fact,v.y*fact);
        this.x+=scaledV.x;
        this.y+=scaledV.y;
        return this;
    }

    public Vector2D subtract(Vector2D v)
    {
        this.x-=v.x;
        this.y-=v.y;
        return this;
    }

    public Vector2D subtract(double ox, double oy)
    {
        this.x-=ox;
        this.y-=oy;
        return this;
    }

    public Vector2D multi(double fact)
    {
        this.x=this.x*fact;
        this.y=this.y*fact;
        return this;
    }

    public Vector2D rotate(double angle)
    {
        double rx =this.x*Math.cos(angle) - this.y*Math.sin(angle);
        double ry = this.x*Math.sin(angle) + this.y*Math.cos(angle);

        this.x=rx;
        this.y=ry;

        return this;

    }

    public double dot(Vector2D v)
    {
        return this.x*v.x + this.y*v.y;
    }

    public double dist(Vector2D v)
    {
        return Math.sqrt( Math.pow( (this.x-v.x),2) + Math.pow( (this.y-v.y),2 ));
    }

    public Vector2D normalise()
    {
        double mag = mag();
        this.x = this.x/mag;
        this.y = this.y/mag;
        return this;
    }

    public Vector2D wrap(double w, double h) // not sure if this is correct
    {
        if(this.x > w)
        {
            this.x=this.x-w;
        }
        if(this.x<0)
        {
            this.x=this.x+w;
        }
        if(this.y>h)
        {
            this.y=this.y-h;
        }
        if(this.y<0)
        {
            this.y = this.y+h;
        }

        return this;
    }

    public static Vector2D polar(double angle, double mag)
    {
        Vector2D v = new Vector2D(mag*Math.cos(angle),mag*Math.sin(angle));
        return v;
    }






}

