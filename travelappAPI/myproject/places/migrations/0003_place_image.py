# Generated by Django 3.2.6 on 2021-10-30 12:05

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('places', '0002_rename_places_place'),
    ]

    operations = [
        migrations.AddField(
            model_name='place',
            name='image',
            field=models.ImageField(default='', upload_to='places/images'),
        ),
    ]
