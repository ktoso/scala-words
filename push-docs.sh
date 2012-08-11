#!/bin/sh
cd docs

make html
make pdf

cd ..

git stash && git stash drop
git checkout gh-pages


find ./**/*html -type f -exec gsed -i 's/_static/static/' {} \;
find ./*html -type f -exec gsed -i 's/_static/static/' {} \;

find ./*html -type f -exec gsed -i 's/_sources/sources/' {} \;
find ./**/*html -type f -exec gsed -i 's/_sources/sources/' {} \;

cp -r docs/_build/html/* .
rm -rf target/ docs/

mv -f _static static
mv -f _sources sources


git add .
git ci -m "Updating documentation @ $(date)"
git push origin gh-pages

git stash && git stash drop
git checkout master

echo "Done!"
