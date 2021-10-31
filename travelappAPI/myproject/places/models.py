from django.db import models

class Place(models.Model):
    name = models.CharField(max_length=100)
    state = models.CharField(max_length=100)
    country = models.CharField(max_length=100)
    tags = models.CharField(max_length=500)

    def __str__(self):
        return self.name