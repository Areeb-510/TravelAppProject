from rest_framework import fields, serializers
from .models import Place

class placeSerializer(serializers.ModelSerializer):

    class Meta:
        model = Place
        fields = '__all__'
