package sc.example.quiz;

import java.io.IOException;
import java.util.Arrays;

import android.provider.BaseColumns;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

import sc.util.StringUtil;
import sc.example.quiz.Quiz;
import sc.example.quiz.Question;

import sc.example.quiz.content.ContentUtil;
import sc.example.quiz.content.QuizContent.Quizes;
import sc.example.quiz.content.QuizContent.Questions;

// This layer is shared between the application which implements the
// quiz ContentProvider (quiz.android.makequiz) and the application
// which uses content URIs to query and manipulate quiz data
// (quiz.android.takequiz).  For convenience, it includes some
// utilities for mapping between URIs and the data model.  If one were
// interested strictly in exposing the content provider API, they
// could create a separate layer just for the
// sc.example.quiz.content.QuizContent interface.
public example.quiz.android.content extends example.quiz.model, android.lib {
}
