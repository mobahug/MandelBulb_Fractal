import peasy.*;

int DIM = 64;
PeasyCam cam;

ArrayList<PVector> mandelbulb = new ArrayList<PVector>();

void setup()
{
  size(600, 600, P3D);
  windowMove(1200, 100);
  cam = new PeasyCam(this, 200);
  //3 loop for each dimension (3D)
  for (int i = 0; i < DIM; i++)
  {
    for (int j = 0; j < DIM; j++)
    {
      boolean edge = false;
      for (int k = 0; k < DIM; k++)
      {
        //giving size of the shape on each dimension
        float x = map(i, 0, DIM, -1, 1);
        float y = map(j, 0, DIM, -1, 1);
        float z = map(k, 0, DIM, -1, 1);

        PVector zeta = new PVector(0, 0, 0);
        int n = 8;
        int maxiterations = 10;
        int iteration = 0;
        while (true)
        {
          Spherical c = spherical(zeta.x, zeta.y, zeta.z);
          float newx = pow(c.r, n) * sin(c.theta * n) * cos(c.phi * n);
          float newy = pow(c.r, n) * sin(c.theta * n) * sin(c.phi * n);
          float newz = pow(c.r, n) * cos(c.theta * n);
          zeta.x = newx + x;
          zeta.y = newy + y;
          zeta.z = newz + z;
          iteration++;

          if (c.r > 16)
          {
            if (edge)
            {
              edge = false;
            }
            break;
          }

          if (iteration > maxiterations)
          {
            if (!edge)
            {
              edge = true;
              mandelbulb.add(new PVector(x * 100, y * 100, z * 100));
            }
            break;
          }
        }
      }
    }
  }
}

class Spherical
{
  float r, theta, phi;
  Spherical(float r, float theta, float phi)
  {
    this.r = r;
    this.theta = theta;
    this.phi = phi;
  }
}

Spherical spherical(float x, float y, float z)
{
  float r = sqrt(x*x + y*y + z*z);
  float theta = atan2(sqrt(x*x + y*y), z);
  float phi = atan2(y, x);
  return new Spherical(r, theta, phi);
}

void draw()
{
  background(0);

  for (PVector v : mandelbulb)
  {
    stroke(255);
    point(v.x, v.y, v.z);
  }

}
