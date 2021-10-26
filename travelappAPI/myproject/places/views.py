from django.shortcuts import render
from django.http import HttpResponse
from django.shortcuts import get_object_or_404
from rest_framework import serializers
from rest_framework import status
from .models import Place
from .serializers import placeSerializer
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

class placeList(APIView):
    def get(self,request):
        place1 = Place.objects.all()
        serializer = placeSerializer(place1,many=True)
        return Response(serializer.data)

    def post(self):
        pass